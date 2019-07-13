package cn.zucc.searchfinal.service.impl;

import cn.zucc.searchfinal.entity.Comment;
import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.mapper.CommentMapper;
import cn.zucc.searchfinal.mapper.MovieMapper;
import cn.zucc.searchfinal.service.CommentService;
import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.ResponseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private String USER_RATINGS_LIST_PREFIX = "USER_RATINGS_LIST_PREFIX";
    private Integer REDIS_MOVIE_RATING_QUEUE_SIZE = 10;

    public CommentServiceImpl() {
    }

    public PageResult listCommentsByDoubanId(Integer doubanId, Integer page, Integer pageSize) {
        if (doubanId == null) {
            return PageResult.ofEmpty();
        } else {
            if (page <= 0) {
                page = 1;
            }
            System.out.println("doubanId:" + doubanId);
            System.out.println("page:" + page);

            PageHelper.startPage(page, pageSize);
            Example example = new Example(Comment.class);
            example.createCriteria().andEqualTo("doubanId", doubanId);
            example.orderBy("createTime").desc();
            List<Comment> list = this.commentMapper.selectByExample(example);
            long total = list.size();
            if (page <= list.size() / 20) {
                list = list.subList((page - 1) * 20, (page) * 20);

            } else {
                list = list.subList((page - 2) * 20, list.size());
            }
            PageInfo pageInfo = new PageInfo(list, pageSize);
            System.out.println(pageInfo.getTotal());
            System.out.println(pageInfo);
            return PageResult.of(total, list);
        }
    }

    public Map<String, Integer> getCommentSegmentsByDoubanId(Integer doubanId) {
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("doubanId", doubanId);
        example.selectProperties(new String[]{"content"});
        List<Comment> list = this.commentMapper.selectByExample(example);
        List<String> contents = (List) list.stream().map(Comment::getContent).collect(Collectors.toList());
        String text = StringUtils.join(contents, " ");
        List<Term> terms = HanLP.segment(text);
        CoreStopWordDictionary.apply(terms);
        Map<String, Integer> map = new HashMap();
        Iterator var8 = terms.iterator();

        while (var8.hasNext()) {
            Term term = (Term) var8.next();
            String word = term.word;
            if (map.containsKey(word)) {
                map.put(word, (Integer) map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        return map;
    }

    public ResponseResult saveComment(Comment comment) {
        this.updateUserRecentRating(comment);
        this.commentMapper.insert(comment);
        return ResponseResult.ok();
    }

    private void updateUserRecentRating(Comment comment) {
        String key = this.USER_RATINGS_LIST_PREFIX + ":" + comment.getDoubanUserId();
        if (this.redisTemplate.hasKey(key) && this.redisTemplate.opsForList().size(key) >= (long) this.REDIS_MOVIE_RATING_QUEUE_SIZE) {
            this.redisTemplate.opsForList().rightPop(key);
        }

        this.redisTemplate.opsForList().leftPush(key, comment.getDoubanId() + ":" + comment.getRatingValue());
    }

    public Comment getComment(Integer doubanUserId, Integer movieId) {
        Movie movie = (Movie) this.movieMapper.selectByPrimaryKey(movieId);
        Integer doubanId = movie.getDoubanId();
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("doubanUserId", doubanUserId).andEqualTo("doubanId", doubanId);
        List<Comment> list = this.commentMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : (Comment) list.get(0);
    }

    public List<Map<String, Object>> countGroupByYear() {
        return this.commentMapper.countGroupByYear();
    }


    public List<Map<String, Object>> countRatingGroupByYear() {
        return this.commentMapper.countRatingGroupByYear();
    }
}

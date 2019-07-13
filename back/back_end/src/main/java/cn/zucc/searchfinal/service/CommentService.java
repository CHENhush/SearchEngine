package cn.zucc.searchfinal.service;




import cn.zucc.searchfinal.entity.Comment;
import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.ResponseResult;


import java.util.List;
import java.util.Map;

public interface CommentService {
    PageResult listCommentsByDoubanId(Integer doubanId, Integer page, Integer pageSize);

    Map<String, Integer> getCommentSegmentsByDoubanId(Integer doubanId);

    ResponseResult saveComment(Comment comment);

    Comment getComment(Integer doubanUserId, Integer movieId);

    List<Map<String, Object>> countGroupByYear();

    List<Map<String, Object>> countRatingGroupByYear();
}

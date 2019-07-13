package cn.zucc.searchfinal.controller;

import cn.zucc.searchfinal.entity.Comment;
import cn.zucc.searchfinal.service.CommentService;
import cn.zucc.searchfinal.service.CommonService;
import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
//    @Autowired
//    private CommonService commonService;

    public CommentController() {
    }

    @GetMapping({"/comment"})
    public Comment getComment(Integer doubanUserId, Integer movieId) {
        return this.commentService.getComment(doubanUserId, movieId);
    }

    @GetMapping({"/comment/save"})
    public ResponseResult saveComment(Comment comment) {
        System.out.println("DEBUG: " + comment);
        return this.commentService.saveComment(comment);
    }

    @GetMapping({"/comment/list"})
    public PageResult<Comment> listCommentsByDoubanId(Integer doubanId, @RequestParam(defaultValue = "1") Integer page) {
        Integer pageSize = 5;
        return this.commentService.listCommentsByDoubanId(doubanId, page, pageSize);
    }

    @GetMapping({"/comment/segments"})
    public Map<String, Integer> getCommentSegments(Integer doubanId) {
        return this.commentService.getCommentSegmentsByDoubanId(doubanId);
    }

    @GetMapping({"/comment/countGroupByYear"})
    public List<Map<String, Object>> countGroupByYear() {
        return this.commentService.countGroupByYear();
    }

    @GetMapping({"/comment/countRatingGroupByYear"})
    public List<Map<String, Object>> countRatingGroupByYear() {
//        System.out.println(this.commentService.countRatingGroupByYear());
        return this.commentService.countRatingGroupByYear();
    }
}


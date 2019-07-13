package cn.zucc.searchfinal.controller;

import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.query.MovieSearchQuery;
import cn.zucc.searchfinal.service.CommonService;
import cn.zucc.searchfinal.service.MovieService;
import cn.zucc.searchfinal.vo.MovieSuggestionVO;
import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.WordCloudItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private CommonService commonService;

    public MovieController() {
    }

    @GetMapping({"/movie/suggest"})
    public PageResult<MovieSuggestionVO> getMovieSearchSuggestions(@RequestParam("keyword") String keyword) {
        return this.movieService.listSearchSuggestions(keyword);
    }

    @GetMapping({"/movie/search"})
    public PageResult<Movie> searchMovie(@RequestParam(defaultValue = "1") Integer page, @RequestParam(value = "searchText",defaultValue = "") String keyword, MovieSearchQuery movieSearchQuery) {
        Integer pageSize = 10;
        return this.movieService.search(page, pageSize, keyword, movieSearchQuery);
    }

    @GetMapping({"/movie/realTimeHotSearch"})
    public PageResult<Movie> getRealTimeHotSearchList(@RequestParam(value = "topN",defaultValue = "10") Integer topN) {
        return this.movieService.listRealTimeHotSearch(topN);
    }

    @GetMapping({"/movie/{id}"})
    public Movie getMovieById(@PathVariable("id") Integer id) {
        return this.movieService.getMovieById(id);
    }

    @GetMapping({"/movie/comment/segments"})
    public PageResult<WordCloudItemVO> getCommentSegments(String text) {
        return this.commonService.getSegments(text);
    }






}

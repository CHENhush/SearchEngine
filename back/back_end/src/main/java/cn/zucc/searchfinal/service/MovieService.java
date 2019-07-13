package cn.zucc.searchfinal.service;



import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.query.MovieSearchQuery;
import cn.zucc.searchfinal.vo.MovieSuggestionVO;
import cn.zucc.searchfinal.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface MovieService {
    PageResult<MovieSuggestionVO> listSearchSuggestions(String keyword);

    PageResult<Movie> search(Integer page, Integer pageSize, String keyword, MovieSearchQuery movieSearchQuery);

    PageResult<Movie> listRealTimeHotSearch(Integer topN);

    Movie getMovieById(Integer id);

}

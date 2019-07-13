package cn.zucc.searchfinal.service;


import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.vo.PageResult;
import org.springframework.stereotype.Service;

//import cn.pancx.searchfinal.vo.PersonMovieVO;

public interface RecommenderService {
    PageResult<Movie> listUserCFMoviesByUserId(Integer userId);
    PageResult<Movie> listUserW2VMoviesByUserId(Integer userId);
    PageResult<Movie> listStreamRecMoviesByUserId(Integer userId);
//    PageResult<PersonMovieVO> listOtherMoviesByActors(Integer movieId);
}

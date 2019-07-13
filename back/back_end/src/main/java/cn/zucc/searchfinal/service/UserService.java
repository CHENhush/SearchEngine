package cn.zucc.searchfinal.service;


import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.entity.User;
import cn.zucc.searchfinal.vo.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    ResponseResult register(User user);

    ResponseResult login(String username, String password);

    ResponseResult logout(String token);

    ResponseResult getUserByToken(String token);

    List<Movie> recommend(int userId);

    void putComment(String s, String value, String url, String name,String text);
}

package cn.zucc.searchfinal.controller;

import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.service.UserService;
import cn.zucc.searchfinal.util.CookieUtils;
import cn.zucc.searchfinal.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {
    private String USER_TOKEN_COOKIE_NAME = "USER_TOKEN_COOKIE_NAME";
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @GetMapping({"/user/login"})
    public ResponseResult login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        ResponseResult result = this.userService.login(username, password);
        if (result.getCode() == 200) {
            System.out.println("200: " + result.getData().toString());
            CookieUtils.setCookie(request, response, this.USER_TOKEN_COOKIE_NAME, result.getData().toString());
        }

        return result;
    }

    @PostMapping({"/user/logout/{token}"})
    public Object logout(@PathVariable String token, String callback, HttpServletResponse response, HttpServletRequest request) {
        ResponseResult result = this.userService.logout(token);
        if (result.getCode() == 200) {
            CookieUtils.deleteCookie(request, response, this.USER_TOKEN_COOKIE_NAME);
        }

        return result;
    }

    @GetMapping({"/user/token/{token}"})
    public ResponseResult getUserByToken(@PathVariable String token) {
        return this.userService.getUserByToken(token);
    }

    @GetMapping({"/user/recommend/{userId}"})
    public List<Movie> recommend(@PathVariable String userId) {
        System.out.println(userId);
        return this.userService.recommend(Integer.parseInt(userId));
    }

    @GetMapping({"/user/comment"})
    public void comment(String value, String url, String name,String movieId,String text) {
        System.out.println(value);
        System.out.println(url);
        System.out.println(name);
        System.out.println(movieId);
        System.out.println(text);
        this.userService.putComment(value,url,name,movieId,text);
    }

}



package cn.zucc.searchfinal.service.impl;


import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.entity.User;
import cn.zucc.searchfinal.fastSimRank.redis.util.RedisOperation;
import cn.zucc.searchfinal.mapper.CommentMapper;
import cn.zucc.searchfinal.mapper.MovieMapper;
import cn.zucc.searchfinal.mapper.UserMapper;
import cn.zucc.searchfinal.service.UserService;
import cn.zucc.searchfinal.util.JsonUtils;
import cn.zucc.searchfinal.vo.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import cn.zucc.searchfinal.fastSimRank.Recommend;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    private String USER_TOKEN_PREFIX = "USER_TOKEN_PREFIX";


    private Integer USER_TOKEN_EXPIRE = 1800;
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public ResponseResult register(User user) {
        return null;
    }

    @Override
    public ResponseResult login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseResult.build(400, "用户名或密码不正确", null, false);
        }

        // Check username
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("doubanUserNickname", username);

        List<User> list = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseResult.build(400, "用户名或密码不正确", null, false);
        }

        // Check password
        User user = list.get(0);
        String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Pwd.equals(user.getPassword())) {
            return ResponseResult.build(400, "用户名或密码不正确", null, false);
        }

        // Generate user token
        String token = UUID.randomUUID().toString();
        String key = USER_TOKEN_PREFIX + ":" + token;
        user.setPassword(null);
        redisTemplate.opsForValue().set(key, JsonUtils.objectToJson(user));
        redisTemplate.expire(key, USER_TOKEN_EXPIRE, TimeUnit.SECONDS);

        return ResponseResult.ok(token);
    }

    @Override
    public ResponseResult logout(String token) {
        String key = USER_TOKEN_PREFIX + ":" + token;
        redisTemplate.delete(key);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult getUserByToken(String token) {
        final String key = USER_TOKEN_PREFIX + ":" + token;
        String json = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(json)) {
            return ResponseResult.build(400, "用户登录会话已过期", null, false);
        }
        redisTemplate.expire(key, USER_TOKEN_EXPIRE, TimeUnit.SECONDS);
        User user = JsonUtils.jsonToPojo(json, User.class);
        return ResponseResult.ok(user);
    }

    public List<Movie> recommend(int userId) {
        List<Movie> recommendMovie = new ArrayList<Movie>();
        try {
            List<Map.Entry<Integer, Double>> recommendIdScore = Recommend.recommend(userId);
            for (Map.Entry<Integer, Double> entry : recommendIdScore) {
//                System.out.println(entry.getKey());
                Movie oneMovie = this.movieMapper.selectOneMovie(entry.getKey());
//                System.out.println(oneMovie.getId());
                recommendMovie.add(oneMovie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (recommendMovie.size() < 10) {
            recommendMovie.add(this.movieMapper.randomSelectOneMovie());
        }
        return recommendMovie;
    }


    public void putComment(String value,String url,String name,String movieId,String text){
        Movie oneMovie = this.movieMapper.selectOneMovie(Integer.parseInt(movieId));
        User oneUser = this.userMapper.selectOneUser(name);
        System.out.println(oneMovie.getDoubanId());
        System.out.println(oneUser.getDoubanUserId());
        System.out.println(text);
        System.out.println(name);
        System.out.println(url);
        System.out.println(new Date());

        this.commentMapper.insertComment(oneMovie.getDoubanId(), oneUser.getDoubanUserId().toString(),text,name,url,Double.parseDouble(value),new Date());
        RedisOperation redisOperation = new RedisOperation();
        redisOperation.addOneEdge(oneMovie.getId(), oneUser.getId(), Integer.parseInt(value));
        this.commentMapper.insertRecord(oneUser.getId(),oneMovie.getId(),Integer.parseInt(value));
        this.commentMapper.insertRecord3(oneUser.getId(),oneMovie.getId(),Integer.parseInt(value));

    }

}

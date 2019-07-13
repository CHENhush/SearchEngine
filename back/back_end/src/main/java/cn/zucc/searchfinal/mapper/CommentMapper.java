package cn.zucc.searchfinal.mapper;

import cn.zucc.searchfinal.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CommentMapper extends Mapper<Comment> {
    @Select({"SELECT DATE_FORMAT(create_time, '%Y') AS year, COUNT(*) AS count FROM comment GROUP BY year ORDER BY year"})
    List<Map<String, Object>> countGroupByYear();

    @MapKey("year")
    @Select({"SELECT DATE_FORMAT(create_time, '%Y') AS year, rating_value, COUNT(*) AS count  FROM comment where rating_value is not null GROUP BY year, rating_value ORDER BY year, rating_value"})
    List<Map<String, Object>> countRatingGroupByYear();


    @Insert({ "insert into comment(douban_id, douban_user_id, content, douban_user_nickname, douban_user_avatar,rating_value,create_time) values(#{douban_id}, #{douban_user_id},#{content}, #{douban_user_nickname}, #{douban_user_avatar}, #{rating_value},#{create_time, jdbcType=TIMESTAMP})" })
    int insertComment(int douban_id, String douban_user_id, String content, String douban_user_nickname, String douban_user_avatar, Double rating_value, Date create_time);


    @Insert({ "insert into record(user_id, movie_id, rating) values(#{user_id}, #{movie_id},#{rating})" })
    int insertRecord(int user_id, int movie_id, int rating);

    @Insert({ "insert into record3(user_id, movie_id, rating) values(#{user_id}, #{movie_id},#{rating})" })
    int insertRecord3(int user_id, int movie_id, int rating);



}


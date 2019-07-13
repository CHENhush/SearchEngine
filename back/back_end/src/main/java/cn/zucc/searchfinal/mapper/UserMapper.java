package cn.zucc.searchfinal.mapper;

import cn.zucc.searchfinal.entity.Movie;
import cn.zucc.searchfinal.entity.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
    @Select({"select id, douban_user_id as doubanUserId, douban_user_nickname as doubanUserNickname, password, douban_user_avatar as doubanUserAvatar from user WHERE douban_user_nickname = #{name}"})
//    @Select({"SELECT * FROM movie WHERE id =er #{id}"})
     User selectOneUser(String name);
}

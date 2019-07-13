package cn.zucc.searchfinal.mapper;

import cn.zucc.searchfinal.entity.Movie;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface MovieMapper extends Mapper<Movie> {
    @Select({"select id, douban_id as doubanId, name, alias, cover_url as coverUrl, year, regions, genres, languages, directors, actors, storyline, mins, release_date as releaseDate, avg_score as avgScore, votes, tags, watch_urls as watchUrls FROM movie WHERE id = #{id}"})
//    @Select({"SELECT * FROM movie WHERE id = #{id}"})
    Movie selectOneMovie(Integer id);

    @Select({"SELECT id, douban_id as doubanId, name, alias, cover_url as coverUrl, year, regions, genres, languages, directors, actors, storyline, mins, release_date as releaseDate, avg_score as avgScore, votes, tags, watch_urls as watchUrls\n" +
            "FROM movie ORDER BY RAND() LIMIT 1"})
    Movie randomSelectOneMovie();




}

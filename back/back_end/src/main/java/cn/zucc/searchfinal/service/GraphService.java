package cn.zucc.searchfinal.service;


import cn.zucc.searchfinal.vo.graph.GraphVO;
import org.springframework.stereotype.Service;

public interface GraphService {
    GraphVO getActedInMovieByActorName(String name);
}

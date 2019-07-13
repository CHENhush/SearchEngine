package cn.zucc.searchfinal.vo.graph;

import lombok.Data;

@Data
public class GraphEdgeVO {
    private Object source;
    private Object target;
    private String relation;
}

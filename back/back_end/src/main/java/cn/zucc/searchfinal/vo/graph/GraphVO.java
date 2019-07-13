package cn.zucc.searchfinal.vo.graph;

import cn.zucc.searchfinal.vo.graph.GraphNodeVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class GraphVO {
    private List<GraphNodeVO> nodes;
    private List<GraphEdgeVO> links;

    public List<GraphNodeVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNodeVO> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdgeVO> getLinks() {
        return links;
    }
    public void setLinks(List<GraphEdgeVO> links) {
        this.links = links;
    }

    public GraphVO(List<GraphNodeVO> nodes, List<GraphEdgeVO> links) {
        this.nodes = nodes;
        this.links = links;
    }



    public static GraphVO ofEmpty() {
        return new GraphVO( new ArrayList<>(), new ArrayList<>());
    }
}

package cn.zucc.searchfinal.service;


import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.WordCloudItemVO;
import org.springframework.stereotype.Service;

public interface CommonService {
    /**
     * 中文分词
     * @param text
     * @return
     */
    PageResult<WordCloudItemVO> getSegments(String text);
}

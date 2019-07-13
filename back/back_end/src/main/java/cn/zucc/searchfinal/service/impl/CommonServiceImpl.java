package cn.zucc.searchfinal.service.impl;


import cn.zucc.searchfinal.service.CommonService;
import cn.zucc.searchfinal.vo.PageResult;
import cn.zucc.searchfinal.vo.WordCloudItemVO;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public PageResult<WordCloudItemVO> getSegments(String text) {
        // 分词
        List<Term> terms = HanLP.segment(text);

        // 统计token词频
        Map<String, Integer> map = new HashMap<>();
        for (Term term : terms) {
            // 根据词性过滤
            // Nature.w: 标点
            // Nature.u: 助词
            char nature = term.nature.firstChar();
            if (nature == 'w' || nature == 'u') {
                continue;
            }
            String word = term.word;
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        List<WordCloudItemVO> wordCloudItemVOList = new ArrayList<>();
        map.forEach((k, v) -> {
            WordCloudItemVO item = new WordCloudItemVO();
            item.setName(k);
            item.setValue(v);
            wordCloudItemVOList.add(item);
        });

        return PageResult.ofList(wordCloudItemVOList);
    }
}

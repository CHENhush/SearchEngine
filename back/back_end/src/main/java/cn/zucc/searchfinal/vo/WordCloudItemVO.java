package cn.zucc.searchfinal.vo;

import lombok.Data;

@Data
public class WordCloudItemVO {
    private String name;
    private Integer value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

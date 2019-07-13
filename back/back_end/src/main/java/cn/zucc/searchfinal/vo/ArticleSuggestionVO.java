package cn.zucc.searchfinal.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ArticleSuggestionVO {
    private String title;
    private String highlightTitle;
}

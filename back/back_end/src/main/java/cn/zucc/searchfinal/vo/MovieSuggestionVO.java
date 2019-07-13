package cn.zucc.searchfinal.vo;


import cn.zucc.searchfinal.entity.Movie;
import lombok.Data;

@Data
public class MovieSuggestionVO extends Movie {
    private String nameHighlight;

    public String getNameHighlight() {
        return nameHighlight;
    }

    public void setNameHighlight(String nameHighlight) {
        this.nameHighlight = nameHighlight;
    }
}

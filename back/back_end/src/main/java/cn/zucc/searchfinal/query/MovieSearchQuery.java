package cn.zucc.searchfinal.query;

import lombok.Data;

@Data
public class MovieSearchQuery {
    private String dateFilter;

    private Integer regionId;

    private Integer minsId;

    public String getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(String dateFilter) {
        this.dateFilter = dateFilter;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getMinsId() {
        return minsId;
    }

    public void setMinsId(Integer minsId) {
        this.minsId = minsId;
    }

    public Integer getYearRangeId() {
        return yearRangeId;
    }

    public void setYearRangeId(Integer yearRangeId) {
        this.yearRangeId = yearRangeId;
    }

    public Integer getSortTypeId() {
        return sortTypeId;
    }

    public void setSortTypeId(Integer sortTypeId) {
        this.sortTypeId = sortTypeId;
    }

    private Integer yearRangeId;

    private Integer sortTypeId;
}

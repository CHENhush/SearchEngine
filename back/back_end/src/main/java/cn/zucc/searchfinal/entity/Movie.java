package cn.zucc.searchfinal.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(
        indexName = "my_index",
        type = "doc",
        shards = 1,
        replicas = 0
)
@Table(
        name = "movie"
)
public class Movie {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Integer id;
    @Column(
            name = "douban_id"
    )
    private Integer doubanId;
    private String name;
    private String alias;
    @Column(
            name = "cover_url"
    )
    private String coverUrl;
    private Integer year;
    private String regions;
    private String genres;
    private String languages;
    private String directors;
    private String actors;
    private String storyline;
    private Integer mins;
    @Column(
            name = "release_date"
    )
    private Date releaseDate;
    @Column(
            name = "avg_score"
    )
    private Double avgScore;
    private Integer votes;
    private String tags;
    @Column(
            name = "watch_urls"
    )
    private String watchUrls;
    @Column(
            name = "douban_type"
    )
    private String doubanType;

    public Movie() {
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getDoubanId() {
        return this.doubanId;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public Integer getYear() {
        return this.year;
    }

    public String getRegions() {
        return this.regions;
    }

    public String getGenres() {
        return this.genres;
    }

    public String getLanguages() {
        return this.languages;
    }

    public String getDirectors() {
        return this.directors;
    }

    public String getActors() {
        return this.actors;
    }

    public String getStoryline() {
        return this.storyline;
    }

    public Integer getMins() {
        return this.mins;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public Double getAvgScore() {
        return this.avgScore;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public String getTags() {
        return this.tags;
    }

    public String getWatchUrls() {
        return this.watchUrls;
    }

    public String getDoubanType() {
        return this.doubanType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDoubanId(Integer doubanId) {
        this.doubanId = doubanId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public void setMins(Integer mins) {
        this.mins = mins;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setWatchUrls(String watchUrls) {
        this.watchUrls = watchUrls;
    }

    public void setDoubanType(String doubanType) {
        this.doubanType = doubanType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Movie)) {
            return false;
        } else {
            Movie other = (Movie)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label239: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label239;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label239;
                    }

                    return false;
                }

                Object this$doubanId = this.getDoubanId();
                Object other$doubanId = other.getDoubanId();
                if (this$doubanId == null) {
                    if (other$doubanId != null) {
                        return false;
                    }
                } else if (!this$doubanId.equals(other$doubanId)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                label218: {
                    Object this$alias = this.getAlias();
                    Object other$alias = other.getAlias();
                    if (this$alias == null) {
                        if (other$alias == null) {
                            break label218;
                        }
                    } else if (this$alias.equals(other$alias)) {
                        break label218;
                    }

                    return false;
                }

                label211: {
                    Object this$coverUrl = this.getCoverUrl();
                    Object other$coverUrl = other.getCoverUrl();
                    if (this$coverUrl == null) {
                        if (other$coverUrl == null) {
                            break label211;
                        }
                    } else if (this$coverUrl.equals(other$coverUrl)) {
                        break label211;
                    }

                    return false;
                }

                Object this$year = this.getYear();
                Object other$year = other.getYear();
                if (this$year == null) {
                    if (other$year != null) {
                        return false;
                    }
                } else if (!this$year.equals(other$year)) {
                    return false;
                }

                Object this$regions = this.getRegions();
                Object other$regions = other.getRegions();
                if (this$regions == null) {
                    if (other$regions != null) {
                        return false;
                    }
                } else if (!this$regions.equals(other$regions)) {
                    return false;
                }

                label190: {
                    Object this$genres = this.getGenres();
                    Object other$genres = other.getGenres();
                    if (this$genres == null) {
                        if (other$genres == null) {
                            break label190;
                        }
                    } else if (this$genres.equals(other$genres)) {
                        break label190;
                    }

                    return false;
                }

                label183: {
                    Object this$languages = this.getLanguages();
                    Object other$languages = other.getLanguages();
                    if (this$languages == null) {
                        if (other$languages == null) {
                            break label183;
                        }
                    } else if (this$languages.equals(other$languages)) {
                        break label183;
                    }

                    return false;
                }

                Object this$directors = this.getDirectors();
                Object other$directors = other.getDirectors();
                if (this$directors == null) {
                    if (other$directors != null) {
                        return false;
                    }
                } else if (!this$directors.equals(other$directors)) {
                    return false;
                }

                label169: {
                    Object this$actors = this.getActors();
                    Object other$actors = other.getActors();
                    if (this$actors == null) {
                        if (other$actors == null) {
                            break label169;
                        }
                    } else if (this$actors.equals(other$actors)) {
                        break label169;
                    }

                    return false;
                }

                Object this$storyline = this.getStoryline();
                Object other$storyline = other.getStoryline();
                if (this$storyline == null) {
                    if (other$storyline != null) {
                        return false;
                    }
                } else if (!this$storyline.equals(other$storyline)) {
                    return false;
                }

                label155: {
                    Object this$mins = this.getMins();
                    Object other$mins = other.getMins();
                    if (this$mins == null) {
                        if (other$mins == null) {
                            break label155;
                        }
                    } else if (this$mins.equals(other$mins)) {
                        break label155;
                    }

                    return false;
                }

                Object this$releaseDate = this.getReleaseDate();
                Object other$releaseDate = other.getReleaseDate();
                if (this$releaseDate == null) {
                    if (other$releaseDate != null) {
                        return false;
                    }
                } else if (!this$releaseDate.equals(other$releaseDate)) {
                    return false;
                }

                Object this$avgScore = this.getAvgScore();
                Object other$avgScore = other.getAvgScore();
                if (this$avgScore == null) {
                    if (other$avgScore != null) {
                        return false;
                    }
                } else if (!this$avgScore.equals(other$avgScore)) {
                    return false;
                }

                label134: {
                    Object this$votes = this.getVotes();
                    Object other$votes = other.getVotes();
                    if (this$votes == null) {
                        if (other$votes == null) {
                            break label134;
                        }
                    } else if (this$votes.equals(other$votes)) {
                        break label134;
                    }

                    return false;
                }

                label127: {
                    Object this$tags = this.getTags();
                    Object other$tags = other.getTags();
                    if (this$tags == null) {
                        if (other$tags == null) {
                            break label127;
                        }
                    } else if (this$tags.equals(other$tags)) {
                        break label127;
                    }

                    return false;
                }

                Object this$watchUrls = this.getWatchUrls();
                Object other$watchUrls = other.getWatchUrls();
                if (this$watchUrls == null) {
                    if (other$watchUrls != null) {
                        return false;
                    }
                } else if (!this$watchUrls.equals(other$watchUrls)) {
                    return false;
                }

                Object this$doubanType = this.getDoubanType();
                Object other$doubanType = other.getDoubanType();
                if (this$doubanType == null) {
                    if (other$doubanType != null) {
                        return false;
                    }
                } else if (!this$doubanType.equals(other$doubanType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Movie;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $doubanId = this.getDoubanId();
        result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $alias = this.getAlias();
        result = result * 59 + ($alias == null ? 43 : $alias.hashCode());
        Object $coverUrl = this.getCoverUrl();
        result = result * 59 + ($coverUrl == null ? 43 : $coverUrl.hashCode());
        Object $year = this.getYear();
        result = result * 59 + ($year == null ? 43 : $year.hashCode());
        Object $regions = this.getRegions();
        result = result * 59 + ($regions == null ? 43 : $regions.hashCode());
        Object $genres = this.getGenres();
        result = result * 59 + ($genres == null ? 43 : $genres.hashCode());
        Object $languages = this.getLanguages();
        result = result * 59 + ($languages == null ? 43 : $languages.hashCode());
        Object $directors = this.getDirectors();
        result = result * 59 + ($directors == null ? 43 : $directors.hashCode());
        Object $actors = this.getActors();
        result = result * 59 + ($actors == null ? 43 : $actors.hashCode());
        Object $storyline = this.getStoryline();
        result = result * 59 + ($storyline == null ? 43 : $storyline.hashCode());
        Object $mins = this.getMins();
        result = result * 59 + ($mins == null ? 43 : $mins.hashCode());
        Object $releaseDate = this.getReleaseDate();
        result = result * 59 + ($releaseDate == null ? 43 : $releaseDate.hashCode());
        Object $avgScore = this.getAvgScore();
        result = result * 59 + ($avgScore == null ? 43 : $avgScore.hashCode());
        Object $votes = this.getVotes();
        result = result * 59 + ($votes == null ? 43 : $votes.hashCode());
        Object $tags = this.getTags();
        result = result * 59 + ($tags == null ? 43 : $tags.hashCode());
        Object $watchUrls = this.getWatchUrls();
        result = result * 59 + ($watchUrls == null ? 43 : $watchUrls.hashCode());
        Object $doubanType = this.getDoubanType();
        result = result * 59 + ($doubanType == null ? 43 : $doubanType.hashCode());
        return result;
    }

    public String toString() {
        return "Movie(id=" + this.getId() + ", doubanId=" + this.getDoubanId() + ", name=" + this.getName() + ", alias=" + this.getAlias() + ", coverUrl=" + this.getCoverUrl() + ", year=" + this.getYear() + ", regions=" + this.getRegions() + ", genres=" + this.getGenres() + ", languages=" + this.getLanguages() + ", directors=" + this.getDirectors() + ", actors=" + this.getActors() + ", storyline=" + this.getStoryline() + ", mins=" + this.getMins() + ", releaseDate=" + this.getReleaseDate() + ", avgScore=" + this.getAvgScore() + ", votes=" + this.getVotes() + ", tags=" + this.getTags() + ", watchUrls=" + this.getWatchUrls() + ", doubanType=" + this.getDoubanType() + ")";
    }
}

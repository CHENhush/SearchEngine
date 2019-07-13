//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.zucc.searchfinal.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "comment"
)
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    @Column(
            name = "douban_id"
    )
    private Integer doubanId;
    @Column(
            name = "douban_comment_id"
    )
    private Integer doubanCommentId;
    private String content;
    @Column(
            name = "douban_user_nickname"
    )
    private String doubanUserNickname;
    @Column(
            name = "douban_user_avatar"
    )
    private String doubanUserAvatar;
    @Column(
            name = "douban_user_url"
    )
    private String doubanUserUrl;
    @Column(
            name = "rating_value"
    )
    private Double ratingValue;
    @Column(
            name = "douban_user_gender"
    )
    private String doubanUserGender;
    @Column(
            name = "douban_user_id"
    )
    private Integer doubanUserId;
    @Column(
            name = "douban_user_loc"
    )
    private String doubanUserLoc;
    private Integer votes;
    @Column(
            name = "create_time"
    )
    private Date createTime;

    public Comment() {
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getDoubanId() {
        return this.doubanId;
    }

    public Integer getDoubanCommentId() {
        return this.doubanCommentId;
    }

    public String getContent() {
        return this.content;
    }

    public String getDoubanUserNickname() {
        return this.doubanUserNickname;
    }

    public String getDoubanUserAvatar() {
        return this.doubanUserAvatar;
    }

    public String getDoubanUserUrl() {
        return this.doubanUserUrl;
    }

    public Double getRatingValue() {
        return this.ratingValue;
    }

    public String getDoubanUserGender() {
        return this.doubanUserGender;
    }

    public Integer getDoubanUserId() {
        return this.doubanUserId;
    }

    public String getDoubanUserLoc() {
        return this.doubanUserLoc;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDoubanId(Integer doubanId) {
        this.doubanId = doubanId;
    }

    public void setDoubanCommentId(Integer doubanCommentId) {
        this.doubanCommentId = doubanCommentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDoubanUserNickname(String doubanUserNickname) {
        this.doubanUserNickname = doubanUserNickname;
    }

    public void setDoubanUserAvatar(String doubanUserAvatar) {
        this.doubanUserAvatar = doubanUserAvatar;
    }

    public void setDoubanUserUrl(String doubanUserUrl) {
        this.doubanUserUrl = doubanUserUrl;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public void setDoubanUserGender(String doubanUserGender) {
        this.doubanUserGender = doubanUserGender;
    }

    public void setDoubanUserId(Integer doubanUserId) {
        this.doubanUserId = doubanUserId;
    }

    public void setDoubanUserLoc(String doubanUserLoc) {
        this.doubanUserLoc = doubanUserLoc;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Comment)) {
            return false;
        } else {
            Comment other = (Comment)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label167: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label167;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label167;
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

                label153: {
                    Object this$doubanCommentId = this.getDoubanCommentId();
                    Object other$doubanCommentId = other.getDoubanCommentId();
                    if (this$doubanCommentId == null) {
                        if (other$doubanCommentId == null) {
                            break label153;
                        }
                    } else if (this$doubanCommentId.equals(other$doubanCommentId)) {
                        break label153;
                    }

                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                label139: {
                    Object this$doubanUserNickname = this.getDoubanUserNickname();
                    Object other$doubanUserNickname = other.getDoubanUserNickname();
                    if (this$doubanUserNickname == null) {
                        if (other$doubanUserNickname == null) {
                            break label139;
                        }
                    } else if (this$doubanUserNickname.equals(other$doubanUserNickname)) {
                        break label139;
                    }

                    return false;
                }

                Object this$doubanUserAvatar = this.getDoubanUserAvatar();
                Object other$doubanUserAvatar = other.getDoubanUserAvatar();
                if (this$doubanUserAvatar == null) {
                    if (other$doubanUserAvatar != null) {
                        return false;
                    }
                } else if (!this$doubanUserAvatar.equals(other$doubanUserAvatar)) {
                    return false;
                }

                label125: {
                    Object this$doubanUserUrl = this.getDoubanUserUrl();
                    Object other$doubanUserUrl = other.getDoubanUserUrl();
                    if (this$doubanUserUrl == null) {
                        if (other$doubanUserUrl == null) {
                            break label125;
                        }
                    } else if (this$doubanUserUrl.equals(other$doubanUserUrl)) {
                        break label125;
                    }

                    return false;
                }

                label118: {
                    Object this$ratingValue = this.getRatingValue();
                    Object other$ratingValue = other.getRatingValue();
                    if (this$ratingValue == null) {
                        if (other$ratingValue == null) {
                            break label118;
                        }
                    } else if (this$ratingValue.equals(other$ratingValue)) {
                        break label118;
                    }

                    return false;
                }

                Object this$doubanUserGender = this.getDoubanUserGender();
                Object other$doubanUserGender = other.getDoubanUserGender();
                if (this$doubanUserGender == null) {
                    if (other$doubanUserGender != null) {
                        return false;
                    }
                } else if (!this$doubanUserGender.equals(other$doubanUserGender)) {
                    return false;
                }

                label104: {
                    Object this$doubanUserId = this.getDoubanUserId();
                    Object other$doubanUserId = other.getDoubanUserId();
                    if (this$doubanUserId == null) {
                        if (other$doubanUserId == null) {
                            break label104;
                        }
                    } else if (this$doubanUserId.equals(other$doubanUserId)) {
                        break label104;
                    }

                    return false;
                }

                label97: {
                    Object this$doubanUserLoc = this.getDoubanUserLoc();
                    Object other$doubanUserLoc = other.getDoubanUserLoc();
                    if (this$doubanUserLoc == null) {
                        if (other$doubanUserLoc == null) {
                            break label97;
                        }
                    } else if (this$doubanUserLoc.equals(other$doubanUserLoc)) {
                        break label97;
                    }

                    return false;
                }

                Object this$votes = this.getVotes();
                Object other$votes = other.getVotes();
                if (this$votes == null) {
                    if (other$votes != null) {
                        return false;
                    }
                } else if (!this$votes.equals(other$votes)) {
                    return false;
                }

                Object this$createTime = this.getCreateTime();
                Object other$createTime = other.getCreateTime();
                if (this$createTime == null) {
                    if (other$createTime != null) {
                        return false;
                    }
                } else if (!this$createTime.equals(other$createTime)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Comment;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $doubanId = this.getDoubanId();
        result = result * 59 + ($doubanId == null ? 43 : $doubanId.hashCode());
        Object $doubanCommentId = this.getDoubanCommentId();
        result = result * 59 + ($doubanCommentId == null ? 43 : $doubanCommentId.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $doubanUserNickname = this.getDoubanUserNickname();
        result = result * 59 + ($doubanUserNickname == null ? 43 : $doubanUserNickname.hashCode());
        Object $doubanUserAvatar = this.getDoubanUserAvatar();
        result = result * 59 + ($doubanUserAvatar == null ? 43 : $doubanUserAvatar.hashCode());
        Object $doubanUserUrl = this.getDoubanUserUrl();
        result = result * 59 + ($doubanUserUrl == null ? 43 : $doubanUserUrl.hashCode());
        Object $ratingValue = this.getRatingValue();
        result = result * 59 + ($ratingValue == null ? 43 : $ratingValue.hashCode());
        Object $doubanUserGender = this.getDoubanUserGender();
        result = result * 59 + ($doubanUserGender == null ? 43 : $doubanUserGender.hashCode());
        Object $doubanUserId = this.getDoubanUserId();
        result = result * 59 + ($doubanUserId == null ? 43 : $doubanUserId.hashCode());
        Object $doubanUserLoc = this.getDoubanUserLoc();
        result = result * 59 + ($doubanUserLoc == null ? 43 : $doubanUserLoc.hashCode());
        Object $votes = this.getVotes();
        result = result * 59 + ($votes == null ? 43 : $votes.hashCode());
        Object $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());
        return result;
    }

    public String toString() {
        return "Comment(id=" + this.getId() + ", doubanId=" + this.getDoubanId() + ", doubanCommentId=" + this.getDoubanCommentId() + ", content=" + this.getContent() + ", doubanUserNickname=" + this.getDoubanUserNickname() + ", doubanUserAvatar=" + this.getDoubanUserAvatar() + ", doubanUserUrl=" + this.getDoubanUserUrl() + ", ratingValue=" + this.getRatingValue() + ", doubanUserGender=" + this.getDoubanUserGender() + ", doubanUserId=" + this.getDoubanUserId() + ", doubanUserLoc=" + this.getDoubanUserLoc() + ", votes=" + this.getVotes() + ", createTime=" + this.getCreateTime() + ")";
    }
}

package cn.zucc.searchfinal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(
        name = "user"
)
public class User {


    @Column(
            name = "id"
    )
    private Integer id;

    @Column(
            name = "douban_user_id"
    )
    private Integer doubanUserId;
    @Column(
            name = "douban_user_nickname"
    )
    private String doubanUserNickname;
    @Column(
            name = "douban_user_avatar"
    )
    private String doubanUserAvatar;
    private String password;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoubanUserId() {
        return this.doubanUserId;
    }

    public String getDoubanUserNickname() {
        return this.doubanUserNickname;
    }

    public String getDoubanUserAvatar() {
        return this.doubanUserAvatar;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDoubanUserId(Integer doubanUserId) {
        this.doubanUserId = doubanUserId;
    }


    public void setDoubanUserNickname(String doubanUserNickname) {
        this.doubanUserNickname = doubanUserNickname;
    }

    public void setDoubanUserAvatar(String doubanUserAvatar) {
        this.doubanUserAvatar = doubanUserAvatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        } else {
            User other = (User) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71:
                {
                    Object this$doubanUserId = this.getDoubanUserId();
                    Object other$doubanUserId = other.getDoubanUserId();
                    if (this$doubanUserId == null) {
                        if (other$doubanUserId == null) {
                            break label71;
                        }
                    } else if (this$doubanUserId.equals(other$doubanUserId)) {
                        break label71;
                    }

                    return false;
                }


                label57:
                {
                    Object this$doubanUserNickname = this.getDoubanUserNickname();
                    Object other$doubanUserNickname = other.getDoubanUserNickname();
                    if (this$doubanUserNickname == null) {
                        if (other$doubanUserNickname == null) {
                            break label57;
                        }
                    } else if (this$doubanUserNickname.equals(other$doubanUserNickname)) {
                        break label57;
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

                Object this$password = this.getPassword();
                Object other$password = other.getPassword();
                if (this$password == null) {
                    if (other$password == null) {
                        return true;
                    }
                } else if (this$password.equals(other$password)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $doubanUserId = this.getDoubanUserId();
        result = result * 59 + ($doubanUserId == null ? 43 : $doubanUserId.hashCode());
        Object $doubanUserNickname = this.getDoubanUserNickname();
        result = result * 59 + ($doubanUserNickname == null ? 43 : $doubanUserNickname.hashCode());
        Object $doubanUserAvatar = this.getDoubanUserAvatar();
        result = result * 59 + ($doubanUserAvatar == null ? 43 : $doubanUserAvatar.hashCode());
        Object $password = this.getPassword();
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "User(doubanUserId=" + this.getDoubanUserId() + ", doubanUserNickname=" + this.getDoubanUserNickname() + ", doubanUserAvatar=" + this.getDoubanUserAvatar() + ", password=" + this.getPassword() + ")";
    }
}

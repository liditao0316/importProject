package com.system.instaKill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2022-03-13
 */
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nickname;

    private String password;

    private String slat;

    private String head;

    private LocalDateTime registerData;

    private LocalDateTime lastLoginDate;

    private Integer loginCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    public LocalDateTime getRegisterData() {
        return registerData;
    }

    public void setRegisterData(LocalDateTime registerData) {
        this.registerData = registerData;
    }
    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", nickname=" + nickname +
            ", password=" + password +
            ", slat=" + slat +
            ", head=" + head +
            ", registerData=" + registerData +
            ", lastLoginDate=" + lastLoginDate +
            ", loginCount=" + loginCount +
        "}";
    }
}

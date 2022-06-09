package com.system.instaKill.vo;

import com.system.instaKill.validator.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 类描述：登陆信息实体类
 */

@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    private String Password;

}

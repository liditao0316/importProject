package com.system.instaKill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 类描述：公共返回对象枚举
 */

@Getter
@ToString
@AllArgsConstructor
public enum ResponseBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登陆模块
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    SESSION_ERROR(500215, "用户SESSION不存在"),

    //秒杀模块
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品没人限购一件")
    ;
    private final Integer code;
    private String message;

}

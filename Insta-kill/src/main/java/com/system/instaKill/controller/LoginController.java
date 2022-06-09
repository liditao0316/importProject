package com.system.instaKill.controller;

import com.system.instaKill.pojo.User;
import com.system.instaKill.service.IUserService;
import com.system.instaKill.utils.CookieUtil;
import com.system.instaKill.vo.LoginVo;
import com.system.instaKill.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@SuppressWarnings({"all"})
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 功能描述： 跳转登陆页面
     * @return 登陆界面
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request){
        return "login";
    }

    /**
     * 功能描述：登陆
     * @param loginVo 解析请求参数
     * @return 返回
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo,request,response);
    }
}

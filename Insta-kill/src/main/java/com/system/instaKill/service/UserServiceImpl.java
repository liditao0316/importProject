package com.system.instaKill.service;

import com.system.instaKill.exception.GlobalException;
import com.system.instaKill.pojo.User;
import com.system.instaKill.mapper.UserMapper;
import com.system.instaKill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.instaKill.utils.CookieUtil;
import com.system.instaKill.utils.Md5Util;
import com.system.instaKill.utils.UUIDUtil;
import com.system.instaKill.vo.LoginVo;
import com.system.instaKill.vo.ResponseBean;
import com.system.instaKill.vo.ResponseBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：用户登陆和用户登陆信息业务类
 *
 * @author liditao
 * @since 2022-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 描述：用户cookie生成，使用redis实现分布式session
     * @param loginVo 存储前端获取的用户名和密码
     * @param request 请求
     * @param response 响应
     * @return 返回ResponseBean类对象
     */
    @Override
    public ResponseBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(ResponseBeanEnum.LOGIN_ERROR);
        }

        //判断密码是否正确
        if (!Md5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(ResponseBeanEnum.LOGIN_ERROR);
        }
        //生成CookieId
        String userTicket = UUIDUtil.uuid();
        //将用户信息存入redis
        redisTemplate.opsForValue().set("user:" + userTicket, user);
        //生成cookie并返回给前端
        CookieUtil.setCookie(request, response, "userTicket", userTicket);
//        Cookie cookie = new Cookie("userTicket",userTicket);
//        cookie.setValue(userTicket);
//        cookie.setDomain("localhost");
//        cookie.setMaxAge(-1);
//        cookie.setPath("/");
//        System.out.println(userTicket);
        return ResponseBean.success(userTicket);
    }

    /**
     * 描述：获取redis中的用户信息
     * @param userTicket 用户CookieTicket
     * @param request 请求
     * @param response 响应
     * @return 用户对象
     */
    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}

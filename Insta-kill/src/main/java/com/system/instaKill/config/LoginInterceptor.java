package com.system.instaKill.config;

import com.system.instaKill.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述：实现判断用户是否登陆拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userCookie = CookieUtil.getCookieValue(request, "userTicket");
        if(StringUtils.isEmpty(userCookie)){
            response.sendRedirect("/login/toLogin");
            return false;
        }
        return true;
    }
}

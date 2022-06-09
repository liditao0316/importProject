package com.system.instaKill.config;

import com.system.instaKill.pojo.User;
import com.system.instaKill.service.IUserService;
import com.system.instaKill.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * MVC配置类
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private IUserService userService;

    /**
     * 描述：添加拦截器，添加拦截信息
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/**/**.js")
                .excludePathPatterns("/**/**.css")
                .excludePathPatterns("/**/**.html");
    }

    /**
     * 描述：添加参数处理Resolvers -> 获取request参数中的cookie并返回user对象
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return User.class == parameter.getParameterType();
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
                HttpServletResponse response = (HttpServletResponse)webRequest.getNativeResponse();
                String ticket = CookieUtil.getCookieValue(request, "userTicket");
                if(StringUtils.isEmpty(ticket)){
                    return null;
                }
                return userService.getUserByCookie(ticket,request,response);
            }
        });
    }
}

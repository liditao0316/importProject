package com.system.instaKill.service;

import com.system.instaKill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.instaKill.vo.LoginVo;
import com.system.instaKill.vo.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-03-13
 */
public interface IUserService extends IService<User> {

    public ResponseBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 描述：根据cookie获取用户
     * @param userUUID
     * @return
     */
    public User getUserByCookie(String userUUID,HttpServletRequest request,HttpServletResponse response);

}

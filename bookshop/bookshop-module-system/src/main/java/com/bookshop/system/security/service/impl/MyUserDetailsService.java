package com.bookshop.system.security.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bookshop.system.security.entity.Users;
import com.bookshop.system.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 功能描述：查询数据库用户名和密码，并返回user对象（对象中包括username，加密后的密码，权限列表）
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersMapper方法，根据用户名查询数据库
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Users::getUsername, username);
        Users user = usersMapper.selectOne(queryWrapper);
        if(user == null){//如果用户名密码不存在
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");
        return new User("lucy",new BCryptPasswordEncoder().encode("123"),auths);
    }
}

package com.bookshop.system.security.service.impl;

import com.bookshop.system.security.entity.Users;
import com.bookshop.system.security.mapper.UsersMapper;
import com.bookshop.system.security.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liditao
 * @since 2022-06-09
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}

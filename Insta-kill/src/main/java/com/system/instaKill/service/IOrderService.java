package com.system.instaKill.service;

import com.system.instaKill.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.instaKill.pojo.User;
import com.system.instaKill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
public interface IOrderService extends IService<Order> {

    Order secKill(User user, GoodsVo good);
}

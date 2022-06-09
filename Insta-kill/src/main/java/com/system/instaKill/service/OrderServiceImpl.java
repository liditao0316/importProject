package com.system.instaKill.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.instaKill.pojo.Order;
import com.system.instaKill.mapper.OrderMapper;
import com.system.instaKill.pojo.SeckillGoods;
import com.system.instaKill.pojo.SeckillOrder;
import com.system.instaKill.pojo.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.instaKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    /**
     * 功能描述：秒杀订单和普通订单的生成
     * @param user 用户
     * @param good 商品信息
     * @return 订单
     */
    @Override
    public Order secKill(User user, GoodsVo good) {
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id",good.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        seckillGoodsService.updateById(seckillGoods);
        //生成普通订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsId(good.getId());
        order.setGoodsName(good.getGoodsName());
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(good.getId());
        seckillOrderService.save(seckillOrder);
        return order;
    }
}

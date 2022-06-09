package com.system.instaKill.rabbitmq;


import com.system.instaKill.pojo.SeckillMessage;
import com.system.instaKill.pojo.SeckillOrder;
import com.system.instaKill.pojo.User;
import com.system.instaKill.service.IGoodsService;
import com.system.instaKill.service.IOrderService;
import com.system.instaKill.utils.JsonUtil;
import com.system.instaKill.vo.GoodsVo;
import com.system.instaKill.vo.ResponseBean;
import com.system.instaKill.vo.ResponseBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    /**
     * 描述：秒杀订单生成
     * @param message
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接收的消息："+message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message,SeckillMessage.class);
        Long goodId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsByGoodsId(goodId);
        if(goodsVo.getStockCount()<1){
            return;
        }
        //判断是否重复抢购
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" +goodId);
        if(seckillOrder != null){
            return;
        }
        //下单操作
        System.out.println(goodsVo);
        orderService.secKill(user,goodsVo);
    }

}

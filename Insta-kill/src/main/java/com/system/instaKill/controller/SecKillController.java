package com.system.instaKill.controller;

import com.system.instaKill.pojo.*;
import com.system.instaKill.rabbitmq.MQSender;
import com.system.instaKill.service.IGoodsService;
import com.system.instaKill.service.IOrderService;
import com.system.instaKill.service.ISeckillOrderService;
import com.system.instaKill.utils.JsonUtil;
import com.system.instaKill.vo.GoodsVo;
import com.system.instaKill.vo.ResponseBean;
import com.system.instaKill.vo.ResponseBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SuppressWarnings({"all"})
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    private Map<Long,Boolean> EmptyStockMap = new HashMap<>();

    /**
     * 功能描述：秒杀商品
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSecKill")
    @ResponseBody
    public ResponseBean doSecKill(Model model, User user, Long goodsId){
        if(user == null){
            return ResponseBean.error(ResponseBeanEnum.SESSION_ERROR);
        }
        //判断是否重复抢购
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" +goodsId);
        if(seckillOrder != null){
            return ResponseBean.error(ResponseBeanEnum.REPEAT_ERROR);
        }

        //内存标记，减少redis的访问
        if(EmptyStockMap.get(goodsId)){
            return ResponseBean.error(ResponseBeanEnum.EMPTY_STOCK);
        }

        //预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if(stock<0){
            EmptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return ResponseBean.error(ResponseBeanEnum.EMPTY_STOCK);
        }

        //使用rabbitmq，将订单信息发送到指定的队列，然后订单处理系统读取队列中的信息，完成订单功能
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
        return ResponseBean.success(0);

    }

    /**
     * 系统初始化，把商品库存加载到Redis中
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(goodsVo -> {
            EmptyStockMap.put(goodsVo.getId(),false);
            redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(),goodsVo.getStockCount());
        });
    }
}

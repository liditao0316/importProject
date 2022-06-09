package com.system.instaKill.service;

import com.system.instaKill.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.instaKill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 功能描述：获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsByGoodsId(Long goodsId);
}

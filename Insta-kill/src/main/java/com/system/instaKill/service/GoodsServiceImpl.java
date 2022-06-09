package com.system.instaKill.service;

import com.system.instaKill.pojo.Goods;
import com.system.instaKill.mapper.GoodsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.instaKill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.findGoodsVo();
    }

    /**
     * 描述：获取商品详情
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo findGoodsByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsByGoodsId(goodsId);
    }
}

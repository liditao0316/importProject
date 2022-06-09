package com.system.instaKill.mapper;

import com.system.instaKill.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.instaKill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 功能描述：获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsByGoodsId(Long goodsId);
}

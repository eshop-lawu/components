package com.lawu.concurrentqueue.mapper.extend;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年2月11日
 * @updateDate 2018年2月11日
 */
@Mapper
public interface SeckillActivityProductDOExtendMapper {

    /**
     * 减去商品库存
     * @param productId 抢购商品id
     * @param num 数量
     * @author jiangxinjun
     * @createDate 2018年2月11日
     * @updateDate 2018年2月11日
     */
    int subtractInventory(@Param("productId") Long productId, @Param("num") Integer num);
}
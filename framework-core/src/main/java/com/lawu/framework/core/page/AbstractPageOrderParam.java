package com.lawu.framework.core.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页排序类型参数
 *
 * @author Leach
 * @date 2017/4/1
 */
public abstract class AbstractPageOrderParam {


    /**
     * 排序类型
     */
    @ApiModelProperty(value = "排序类型，ASC正序（默认），DESC反序")
    private OrderType orderType = OrderType.ASC;

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}

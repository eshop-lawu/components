package com.lawu.framework.core.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Leach
 * @date 2017/3/23
 */
@Deprecated
public abstract class PageParam {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", required = true)
    private Integer currentPage = 0;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize = 20;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", required = true)
    private String orderField;

    /**
     * 排序类型
     */
    @ApiModelProperty(value = "排序类型，ASC正序（默认），DESC反序")
    private OrderType orderType = OrderType.ASC;

    public int getOffset() {
        return this.pageSize * this.currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}

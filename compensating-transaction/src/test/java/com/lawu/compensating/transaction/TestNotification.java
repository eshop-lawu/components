package com.lawu.compensating.transaction;

/**
 * 
 * @author jiangxinjun
 * @createDate 2017年12月26日
 * @updateDate 2017年12月26日
 */
public class TestNotification extends Notification {
    
    private static final long serialVersionUID = 4546170495366654211L;
    
    /**
     * 抢购商品id
     */
    private Long seckillActivityProductId;
    
    /**
     * 数量
     */
    private Integer quantity;
    
    /**
     * 订单id
     */
    private Long shoppingOrderId;
    
    public Long getSeckillActivityProductId() {
        return seckillActivityProductId;
    }

    public void setSeckillActivityProductId(Long seckillActivityProductId) {
        this.seckillActivityProductId = seckillActivityProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShoppingOrderId() {
        return shoppingOrderId;
    }

    public void setShoppingOrderId(Long shoppingOrderId) {
        this.shoppingOrderId = shoppingOrderId;
    }
    
}

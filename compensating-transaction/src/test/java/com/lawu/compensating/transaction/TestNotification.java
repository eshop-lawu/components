package com.lawu.compensating.transaction;

/**
 * 
 * @author jiangxinjun
 * @createDate 2017年12月26日
 * @updateDate 2017年12月26日
 */
public class TestNotification extends Notification {
    
    private static final long serialVersionUID = 4546170495366654211L;
    
    private Long seckillActivityProductId;
    
    private Integer quantity;

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
}

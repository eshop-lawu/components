package com.lawu.compensating.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.compensating.transaction.domain.ShoppingOrderDO;
import com.lawu.compensating.transaction.mapper.ShoppingOrderDOMapper;
import com.lawu.compensating.transaction.service.impl.AbstractTransactionMainService;

/**
 * 
 * @author jiangxinjun
 * @createDate 2017年12月25日
 * @updateDate 2017年12月25日
 */
@Service
@CompensatingTransactionMain(value = TransactionTest.TYPE, topic = TransactionTest.TOPIC, tags = TransactionTest.TAGS + "2")
public class TestTransactionMainServiceImpl2 extends AbstractTransactionMainService<TestNotification, TestReply2> {
    
    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    
    @Override
    public TestNotification selectNotification(Long shoppingOrderId) {
        ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderId);
        TestNotification notification = new TestNotification();
        notification.setQuantity(1);
        notification.setSeckillActivityProductId(shoppingOrderDO.getActivityProductId());
        notification.setShoppingOrderId(shoppingOrderId);
        return notification;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void afterSuccess(Long shoppingOrderId, TestReply2 reply) {
        ShoppingOrderDO shoppingOrderUpdate = new ShoppingOrderDO();
        shoppingOrderUpdate.setId(shoppingOrderId);
        if (reply.getIsSuccess()) {
            shoppingOrderUpdate.setOrderStatus((byte) 0x01);
        } else {
            shoppingOrderUpdate.setOrderStatus((byte) 0x05);
        }
        shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderUpdate);
    }
}

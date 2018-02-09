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
@Service("testTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionTest.TYPE, topic = TransactionTest.TOPIC, tags = TransactionTest.TAGS)
public class TestTransactionMainServiceImpl extends AbstractTransactionMainService<TestNotification, TestReply> {
    
    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    
    @Override
    public TestNotification selectNotification(Long shoppingOrderId) {
        ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderId);
        TestNotification notification = new TestNotification();
        notification.setQuantity(1);
        notification.setSeckillActivityProductId(shoppingOrderDO.getActivityProductId());
        return notification;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void afterSuccess(Long shoppingOrderId, TestReply reply) {
        ShoppingOrderDO shoppingOrderUpdate = new ShoppingOrderDO();
        shoppingOrderUpdate.setId(shoppingOrderId);
        shoppingOrderUpdate.setOrderStatus((byte)0x01);
        shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderUpdate);
        TransactionTest.countDownLatch.countDown();
    }
}

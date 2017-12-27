package com.lawu.compensating.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.compensating.transaction.domain.SeckillActivityProductDO;
import com.lawu.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.compensating.transaction.mapper.SeckillActivityProductDOMapper;

/**
 * 
 * @author jiangxinjun
 * @createDate 2017年12月25日
 * @updateDate 2017年12月25日
 */
@Service("testTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = TransactionTest.TOPIC, tags = TransactionTest.TAGS)
public class TestTransactionFollowServiceImpl extends AbstractTransactionFollowService<TestNotification, TestReply> {

    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
	@Override
	@Transactional
	public void execute(TestNotification notification) {
	    SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(notification.getSeckillActivityProductId());
	    SeckillActivityProductDO seckillActivityProductUpdateDO = new SeckillActivityProductDO();
	    seckillActivityProductUpdateDO.setId(notification.getSeckillActivityProductId());
	    seckillActivityProductUpdateDO.setLeftCount(seckillActivityProductDO.getLeftCount() - notification.getQuantity());
	    seckillActivityProductDOMapper.updateByPrimaryKeySelective(seckillActivityProductUpdateDO);
	}
	
	@Override
	public TestReply getReply(TestNotification notification) {
	    return new TestReply();
	}
}

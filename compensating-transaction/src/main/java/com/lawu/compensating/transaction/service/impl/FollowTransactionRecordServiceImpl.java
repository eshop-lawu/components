package com.lawu.compensating.transaction.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.domain.FollowTransactionRecordDO;
import com.lawu.compensating.transaction.domain.FollowTransactionRecordDOExample;
import com.lawu.compensating.transaction.mapper.FollowTransactionRecordDOMapper;
import com.lawu.compensating.transaction.service.FollowTransactionRecordService;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public class FollowTransactionRecordServiceImpl implements FollowTransactionRecordService {
	
	@Autowired
	private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
	
//    @Autowired
//    private TransactionProperties transactionProperties;

	/**
	 * 判断MQ消息是否被成功消费
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @return
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	@Override
	public boolean isExist(String topic, Long transationId) {
		FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
		example.createCriteria().andTopicEqualTo(topic).andTransationIdEqualTo(transationId);
		followTransactionRecordDOMapper.countByExample(example);
		return followTransactionRecordDOMapper.countByExample(example) > 0 ? true : false;
	}

	/**
	 * 消息被消费成功，保存一条记录
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	@Transactional
	@Override
	public void consumptionSuccessful(String topic, Long transationId) {
		FollowTransactionRecordDO followTransactionRecordDO = new FollowTransactionRecordDO();
		followTransactionRecordDO.setTransationId(transationId);
		followTransactionRecordDO.setTopic(topic);
		followTransactionRecordDO.setGmtCreate(new Date());
		followTransactionRecordDOMapper.insert(followTransactionRecordDO);
		
	    /*
        TODO 改为定时任务执行,因为每次消费都去执行,增加数据库的压力
		// 删除指定时间之前已经处理的主事务消息
		FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,  - transactionProperties.getDeleteRecordTime());
        example.createCriteria().andGmtCreateLessThanOrEqualTo(calendar.getTime());
        followTransactionRecordDOMapper.deleteByExample(example);
        */
	} 
	
}

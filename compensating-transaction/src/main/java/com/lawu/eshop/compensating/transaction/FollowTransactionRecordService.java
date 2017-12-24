package com.lawu.eshop.compensating.transaction;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public interface FollowTransactionRecordService {
	
	/**
	 * 判断MQ消息是否被成功消费
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @return
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	boolean isExist(String topic, Long transationId);
	
	/**
	 * 消息被消费成功，保存一条记录
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @author Sunny
	 * @date 2017年6月1日
	 */
	void consumptionSuccessful(String topic, Long transationId) throws MySQLIntegrityConstraintViolationException;
	
}

package com.lawu.compensating.transaction.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.Notification;
import com.lawu.compensating.transaction.Reply;
import com.lawu.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.compensating.transaction.properties.TransactionProperties;
import com.lawu.compensating.transaction.properties.TransactionProperties.TransactionJob;
import com.lawu.compensating.transaction.service.TransactionMainService;
import com.lawu.compensating.transaction.service.TransactionStatusService;
import com.lawu.compensating.transaction.util.SpringApplicationContextTool;
import com.lawu.mq.message.MessageProducerService;
import com.lawu.synchronization.lock.service.LockService;

/**
 * 补偿性事务主逻辑服务抽象类
 * @author Leach
 * @date 2017/3/29
 */
public abstract class AbstractTransactionMainService<N extends Notification, R extends Reply> implements TransactionMainService<R> {

	private static Logger logger = LoggerFactory.getLogger(AbstractTransactionMainService.class);
	
    @Autowired
    private MessageProducerService messageProducerService;
    
    @Autowired
    private LockService lockService;
    
    @Autowired
    private TransactionStatusService transactionStatusService;
    
    @Autowired
    private TransactionProperties transactionProperties;
    
    private CompensatingTransactionMain annotation = this.getClass().getAnnotation(CompensatingTransactionMain.class);

    private byte type = annotation.value();

    private String topic = annotation.topic();

    private String tags = annotation.tags();
    
    @Autowired
    private SpringApplicationContextTool springApplicationContextTool;

    /**
     * 查询需要发送到其他模块的数据
     *
     * @param relateId
     * @return
     */
    public abstract N selectNotification(Long relateId);

    /**
     * 事务成功回调时，需要执行的逻辑
     * 默认为空，需要的话可以Override
     * 
     * @param relateId
     * @param reply
     */
    public void afterSuccess(Long relateId, R reply) {
        return;
    }

    @Override
    public void sendNotice(Long relateId) {

        N notification = selectNotification(relateId);

        if (notification == null) {
            throw new IllegalArgumentException("Can't find the notification by relateId: " + relateId);
        }
        Long transactionId = transactionStatusService.save(relateId, type);
        notification.setTransactionId(transactionId);

        messageProducerService.sendMessage(topic, tags, notification);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void receiveCallback(R reply, long storeTimestamp) {
        if (new Date().getTime() - storeTimestamp >= transactionProperties.getMessageValidTime()) {
            logger.info("回调消息已经失效");
            return;
        }
        // 统一处理事务异常，手动捕捉异常，并且打印错误信息
        StringBuilder locakName = new StringBuilder();
        locakName.append(topic).append("_").append(annotation.tags() + "-reply").append("_").append(reply.getTransactionId());
        // 从事务幂等性保证，表中存在记录，说明消息已经被成功消费，直接返回
        if (transactionStatusService.isSuccess(reply.getTransactionId())) {
            logger.info("回调消息已经被消费");
            return;
        }
        // 如果没有获取到锁直接返回
        if (!lockService.tryLock(locakName.toString())) {
            logger.info("锁还未释放");
            return;
        }
        try {
            TransactionMainService<R> proxy = springApplicationContextTool.getProxy(this, TransactionMainService.class);
            proxy.executeCallback(reply);
        } catch (Exception e) {
            logger.error("回调事务执行异常", e);
            // 抛出异常，回滚事务
            throw e;
        } finally {
            /*
             * 事务执行完成释放锁 无论是否有异常都释放锁
             */
            lockService.unLock(locakName.toString());
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executeCallback(R reply) {
        Long relateId = transactionStatusService.success(reply.getTransactionId());
        if (relateId == null) {
            logger.info("回调消息已消费");
            return;
        }
        afterSuccess(relateId, reply);
    }

    @Override
    public void check(Long count) {
        TransactionJob transactionJob = transactionProperties.getJob();
        List<TransactionRecordBO> notDoneList = transactionStatusService.selectNotDoneList(type, transactionJob.getExectotalCount());
        for (int i = 0; i < notDoneList.size(); i++) {
        	TransactionRecordBO transactionRecordBO = notDoneList.get(i);
        	
        	/*
        	 * 当前的执行总次数  / ( 执行次数  ^ 基数) == 0
        	 * 执行次数 < 可执行的次数
        	 */
        	if (transactionRecordBO.getTimes() < transactionJob.getExectotalCount() && count % (long) Math.pow(transactionJob.getIntervalBaseNumber(), transactionRecordBO.getTimes()) == 0) {
	            N notification = selectNotification(transactionRecordBO.getRelateId());
	            if (notification == null) {
	                throw new IllegalArgumentException("Can't find the notification by relateId: " + transactionRecordBO.getRelateId());
	            }
	            notification.setTransactionId(transactionRecordBO.getId());
	            
	            // 更新执行次数
                transactionStatusService.updateTimes(transactionRecordBO.getId(), transactionRecordBO.getTimes() + 1);
	            
	            messageProducerService.sendMessage(topic, tags, notification);
        	}
        }
    }

    @Override
	public String getTopic() {
		return topic;
	}
}

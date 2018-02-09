package com.lawu.compensating.transaction.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.compensating.transaction.service.FollowTransactionRecordService;
import com.lawu.compensating.transaction.service.TransactionStatusService;

/**
 * 物理删除配置时间之前的所有事物记录
 * @author jiangxinjun
 * @createDate 2018年2月8日
 * @updateDate 2018年2月8日
 */
public class DeleteTransactionRecordJob implements SimpleJob {
    
    /**
     * 考虑可能没有开启主事务<p>
     * 设置<code>required = false<code>
     */
    @Autowired(required = false)
    public TransactionStatusService transactionStatusService;
    
    /**
     * 考虑可能没有开启从事务<p>
     * 设置<code>required = false<code>
     */
    @Autowired(required = false)
    public FollowTransactionRecordService followTransactionRecordService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        // 先删除主事务再删除从事务
        if (transactionStatusService != null) {
            transactionStatusService.deleteExpiredRecords();
            
        }
        if (followTransactionRecordService != null) {
            followTransactionRecordService.deleteExpiredRecords();
        }
    }
}

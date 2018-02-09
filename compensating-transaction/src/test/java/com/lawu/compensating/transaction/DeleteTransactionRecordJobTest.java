package com.lawu.compensating.transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.lawu.compensating.transaction.domain.FollowTransactionRecordDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.job.DeleteTransactionRecordJob;
import com.lawu.compensating.transaction.mapper.FollowTransactionRecordDOMapper;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;
import com.lawu.compensating.transaction.properties.TransactionProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class DeleteTransactionRecordJobTest {
    
    @Autowired
    private DeleteTransactionRecordJob deleteTransactionRecordJob;
    
    @BeforeClass
    public static void before() throws Exception {
        EmbeddedRedis.start();
        //EmbedZKServer.start();
    }
    
    @AfterClass
    public static void after() throws IOException {
        EmbeddedRedis.stop();
    }
    
    @Autowired
    private TransactionProperties transactionProperties;
    
    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;
    
    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @Test
    public void deleteTransactionRecordJobTest() throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 设置时分秒为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, - transactionProperties.getDeleteRecordTime());
        
        List<TransactionRecordDO> transactionRecordDOList = new ArrayList<>();
        for (int i = 1; i <= 101; i++) {
            TransactionRecordDO transactionRecord = new TransactionRecordDO();
            transactionRecord.setGmtModified(new Date());
            transactionRecord.setGmtCreate(calendar.getTime());
            transactionRecord.setIsProcessed(true);
            transactionRecord.setRelateId(Long.valueOf(i));
            transactionRecord.setTimes(0L);
            transactionRecord.setType((byte) 0);
            transactionRecordDOMapper.insert(transactionRecord);
            transactionRecordDOList.add(transactionRecord);
        }
        
        List<FollowTransactionRecordDO> followTransactionRecordDOList = new ArrayList<>();
        for (int i = 1; i <= 101; i++) {
            FollowTransactionRecordDO followTransactionRecord = new FollowTransactionRecordDO();
            followTransactionRecord.setGmtCreate(calendar.getTime());
            followTransactionRecord.setTopic("topic");
            followTransactionRecord.setTransationId(Long.valueOf(i));
            followTransactionRecordDOMapper.insert(followTransactionRecord);
            followTransactionRecordDOList.add(followTransactionRecord);
        }
        
        ShardingContexts shardingContexts = new ShardingContexts("taskId", "jobName", 1, "jobParameter", new HashMap<>());
        ShardingContext shardingContext = new ShardingContext(shardingContexts, 1);
        deleteTransactionRecordJob.execute(shardingContext);
        
        for (FollowTransactionRecordDO item : followTransactionRecordDOList) {
            FollowTransactionRecordDO followTransactionRecordAcutal = followTransactionRecordDOMapper.selectByPrimaryKey(item.getId());
            Assert.assertNull(followTransactionRecordAcutal);
        }
        
        for (TransactionRecordDO item : transactionRecordDOList) {
            TransactionRecordDO transactionRecordDOAcutal = transactionRecordDOMapper.selectByPrimaryKey(item.getId());
            Assert.assertNull(transactionRecordDOAcutal);
        }
    }
    
}

package com.lawu.mybatis;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.mybatis.domain.TransactionRecordDO;
import com.lawu.mybatis.mapper.AnnotationTransactionRecordDOMapper;
import com.lawu.mybatis.mapper.TransactionRecordDOMapper;
import com.lawu.mybatis.service.AnnotationTransactionRecordService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    
    private static final Logger log = LoggerFactory.getLogger(MybatisTest.class);
    
    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;
    
    @Autowired
    private AnnotationTransactionRecordDOMapper annotationTransactionRecordDOMapper;
    
    @Autowired
    private AnnotationTransactionRecordService annotationTransactionRecordService;
    
    @Test
    public void xmlConfiguration() {
        TransactionRecordDO transactionRecordDO = new TransactionRecordDO();
        transactionRecordDO.setGmtCreate(new Date());
        transactionRecordDO.setGmtModified(new Date());
        transactionRecordDO.setIsProcessed(false);
        transactionRecordDO.setRelateId(1L);
        transactionRecordDO.setTimes(0L);
        transactionRecordDO.setType((byte) 0x01);
        transactionRecordDOMapper.insert(transactionRecordDO);
        Assert.assertNotNull(transactionRecordDO.getId());
        transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
        Assert.assertNotNull(transactionRecordDO);
    }
    
    @Test
    public void annotationConfiguration() {
        TransactionRecordDO transactionRecordDO = new TransactionRecordDO();
        transactionRecordDO.setGmtCreate(new Date());
        transactionRecordDO.setGmtModified(new Date());
        transactionRecordDO.setIsProcessed(false);
        transactionRecordDO.setRelateId(1L);
        transactionRecordDO.setTimes(0L);
        transactionRecordDO.setType((byte) 0x01);
        annotationTransactionRecordDOMapper.insert(transactionRecordDO);
        Assert.assertNotNull(transactionRecordDO.getId());
        transactionRecordDO.setIsProcessed(true);
        annotationTransactionRecordDOMapper.update(transactionRecordDO);
        transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
        Assert.assertNotNull(transactionRecordDO);
        Assert.assertEquals(true, transactionRecordDO.getIsProcessed());
    }
    
    @Test
    public void transaction() {
        TransactionRecordDO transactionRecordDO = new TransactionRecordDO();
        transactionRecordDO.setGmtCreate(new Date());
        transactionRecordDO.setGmtModified(new Date());
        transactionRecordDO.setIsProcessed(false);
        transactionRecordDO.setRelateId(1L);
        transactionRecordDO.setTimes(0L);
        transactionRecordDO.setType((byte) 0x01);
        try {
            annotationTransactionRecordService.insert(transactionRecordDO);
        } catch (Exception e) {
            log.error("保存失败", e);
        }
        transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
        Assert.assertNull(transactionRecordDO);
    }
}

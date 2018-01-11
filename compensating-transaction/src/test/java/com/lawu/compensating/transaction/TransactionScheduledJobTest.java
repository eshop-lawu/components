package com.lawu.compensating.transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.compensating.transaction.domain.FollowTransactionRecordDOExample;
import com.lawu.compensating.transaction.domain.SeckillActivityProductDO;
import com.lawu.compensating.transaction.domain.ShoppingOrderDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.mapper.FollowTransactionRecordDOMapper;
import com.lawu.compensating.transaction.mapper.SeckillActivityProductDOMapper;
import com.lawu.compensating.transaction.mapper.ShoppingOrderDOMapper;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class TransactionScheduledJobTest {

    @BeforeClass
    public static void before() throws Exception {
        EmbeddedRedis.start();
        EmbedZKServer.start();
    }
    
    @AfterClass
    public static void after() throws IOException {
        EmbeddedRedis.stop();
    }
    
    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;
    
    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
    
    @Ignore
    @Test
    public void transactionScheduledJobTest() throws InterruptedException {
        SeckillActivityProductDO seckillActivityProductDO = new SeckillActivityProductDO();
        seckillActivityProductDO.setActivityId(1L);
        seckillActivityProductDO.setAttentionCount(0);
        seckillActivityProductDO.setAuditorAccount("admin");
        seckillActivityProductDO.setAuditTime(new Date());
        seckillActivityProductDO.setGmtCreate(new Date());
        seckillActivityProductDO.setGmtModified(new Date());
        seckillActivityProductDO.setLeftCount(1);
        seckillActivityProductDO.setMerchantId(1L);
        seckillActivityProductDO.setOriginalPrice(new BigDecimal(0));
        seckillActivityProductDO.setProductId(1L);
        seckillActivityProductDO.setProductModelCount(1);
        seckillActivityProductDO.setProductName("test");
        seckillActivityProductDO.setProductPicture("test.png");
        seckillActivityProductDO.setStatus((byte)0x01);
        seckillActivityProductDO.setTurnover(new BigDecimal(0));
        seckillActivityProductDO.setReasons("");
        seckillActivityProductDOMapper.insert(seckillActivityProductDO);
        
        ShoppingOrderDO expected = new ShoppingOrderDO();
        expected.setCommodityTotalPrice(new BigDecimal(1));
        expected.setActualAmount(new BigDecimal(1));
        expected.setFreightPrice(new BigDecimal(0));
        expected.setGmtCreate(new Date());
        expected.setGmtModified(new Date());
        expected.setIsFans(true);
        expected.setIsNeedsLogistics(true);
        expected.setIsNoReasonReturn(false);
        expected.setMemberId(1L);
        expected.setMemberNum("M0001");
        expected.setMerchantId(1L);
        expected.setMerchantName("拉乌网络");
        expected.setMerchantStoreId(1L);
        expected.setMerchantNum("B0001");
        expected.setOrderStatus((byte)0x00);
        expected.setCommissionStatus((byte)0x00);
        expected.setOrderTotalPrice(new BigDecimal(1));
        expected.setOrderNum("O00001");
        expected.setStatus((byte)0x01);
        expected.setConsigneeAddress("大冲商务中心1301");
        expected.setConsigneeMobile("123456");
        expected.setConsigneeName("Sunny");
        expected.setIsDone(false);
        expected.setShoppingCartIdsStr("1");
        expected.setSendTime(0);
        expected.setActivityId(seckillActivityProductDO.getActivityId());
        expected.setActivityProductId(seckillActivityProductDO.getId());
        shoppingOrderDOMapper.insertSelective(expected);
        
        TransactionRecordDO transactionRecordDO = new TransactionRecordDO();
        transactionRecordDO.setRelateId(expected.getId());
        transactionRecordDO.setGmtCreate(new Date());
        transactionRecordDO.setGmtModified(new Date());
        transactionRecordDO.setIsProcessed(false);
        transactionRecordDO.setTimes(0L);
        transactionRecordDO.setType(TransactionTest.TYPE);
        transactionRecordDOMapper.insert(transactionRecordDO);
        
        //等待事务执行完成
        TransactionTest.countDownLatch.await();
        
        // 发送消息是否正常
        transactionRecordDO = transactionRecordDOMapper.selectByPrimaryKey(transactionRecordDO.getId());
        Assert.assertEquals(true, transactionRecordDO.getIsProcessed());
        Assert.assertEquals(1L, transactionRecordDO.getTimes().longValue());
        
        FollowTransactionRecordDOExample followTransactionRecordDOExample = new FollowTransactionRecordDOExample();
        followTransactionRecordDOExample.createCriteria().andTransationIdEqualTo(transactionRecordDO.getId()).andTopicEqualTo(TransactionTest.TOPIC);
        Long count = followTransactionRecordDOMapper.countByExample(followTransactionRecordDOExample);
        Assert.assertEquals(1L, count.longValue());
        
        ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actualShoppingOrderDO);
        Assert.assertEquals(Byte.valueOf((byte)0x01), actualShoppingOrderDO.getOrderStatus());
    }
    
}

package com.lawu.compensating.transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.compensating.transaction.domain.FollowTransactionRecordDO;
import com.lawu.compensating.transaction.domain.FollowTransactionRecordDOExample;
import com.lawu.compensating.transaction.domain.SeckillActivityProductDO;
import com.lawu.compensating.transaction.domain.ShoppingOrderDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDOExample;
import com.lawu.compensating.transaction.mapper.FollowTransactionRecordDOMapper;
import com.lawu.compensating.transaction.mapper.SeckillActivityProductDOMapper;
import com.lawu.compensating.transaction.mapper.ShoppingOrderDOMapper;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;
import com.lawu.compensating.transaction.properties.TransactionProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class TransactionTest {
    
    public final static String TOPIC = "test_topic";
    
    public final static String TAGS = "test_tag";
    
    public final static byte TYPE = 0x01;
    
    public final static CountDownLatch countDownLatch = new CountDownLatch(1);
    
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
    @Qualifier("testTransactionMainServiceImpl")
    private TransactionMainService<TestReply> testTransactionMainServiceImpl;
    
    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Autowired
    private TransactionRecordDOMapper transactionRecordDOMapper;
    
    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;
    
    @Autowired
    private TransactionProperties transactionProperties;
    
    @Ignore
    @Test
    public void sendNotice() throws InterruptedException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, - transactionProperties.getDeleteRecordTime());
        
        TransactionRecordDO transactionRecord = new TransactionRecordDO();
        transactionRecord.setGmtModified(new Date());
        transactionRecord.setGmtCreate(calendar.getTime());
        transactionRecord.setIsProcessed(true);
        transactionRecord.setRelateId(1L);
        transactionRecord.setTimes(0L);
        transactionRecord.setType((byte) 0);
        transactionRecordDOMapper.insert(transactionRecord);
        
        FollowTransactionRecordDO followTransactionRecord = new FollowTransactionRecordDO();
        followTransactionRecord.setGmtCreate(calendar.getTime());
        followTransactionRecord.setTopic(TOPIC);
        followTransactionRecord.setTransationId(1L);
        followTransactionRecordDOMapper.insert(followTransactionRecord);
        
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
        
        // 模拟重复消息
        testTransactionMainServiceImpl.sendNotice(expected.getId());
        
        //等待事务执行完成
        countDownLatch.await(5000, TimeUnit.MILLISECONDS);
        
        // 发送消息是否正常
        TransactionRecordDOExample example = new TransactionRecordDOExample();
        example.createCriteria().andRelateIdEqualTo(expected.getId()).andTypeEqualTo(TransactionTest.TYPE);
        TransactionRecordDO transactionRecordDO = transactionRecordDOMapper.selectByExample(example).get(0);
        Assert.assertNotNull(transactionRecordDO);
        Assert.assertNotNull(transactionRecordDO.getGmtModified());
        Assert.assertNotNull(transactionRecordDO.getGmtCreate());
        Assert.assertNotNull(transactionRecordDO.getId());
        Assert.assertNotNull(transactionRecordDO.getType());
        Assert.assertEquals(true, transactionRecordDO.getIsProcessed());
        Assert.assertEquals(expected.getId(), transactionRecordDO.getRelateId());
        Assert.assertEquals(0L, transactionRecordDO.getTimes().longValue());
        
        FollowTransactionRecordDOExample followTransactionRecordDOExample = new FollowTransactionRecordDOExample();
        followTransactionRecordDOExample.createCriteria().andTransationIdEqualTo(transactionRecordDO.getId()).andTopicEqualTo(TransactionTest.TOPIC);
        Long count = followTransactionRecordDOMapper.countByExample(followTransactionRecordDOExample);
        Assert.assertEquals(1L, count.longValue());
        
        ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(expected.getId());
        Assert.assertNotNull(actualShoppingOrderDO);
        Assert.assertEquals(Byte.valueOf((byte)0x01), actualShoppingOrderDO.getOrderStatus());
        
        FollowTransactionRecordDO followTransactionRecordAcutal = followTransactionRecordDOMapper.selectByPrimaryKey(followTransactionRecord.getId());
        Assert.assertNull(followTransactionRecordAcutal);
        TransactionRecordDO transactionRecordDOAcutal = transactionRecordDOMapper.selectByPrimaryKey(followTransactionRecord.getId());
        Assert.assertNull(transactionRecordDOAcutal);
    }
}

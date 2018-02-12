package com.lawu.compensating.transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.lawu.compensating.transaction.domain.FollowTransactionRecordDOExample;
import com.lawu.compensating.transaction.domain.SeckillActivityProductDO;
import com.lawu.compensating.transaction.domain.ShoppingOrderDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDOExample;
import com.lawu.compensating.transaction.mapper.FollowTransactionRecordDOMapper;
import com.lawu.compensating.transaction.mapper.SeckillActivityProductDOMapper;
import com.lawu.compensating.transaction.mapper.ShoppingOrderDOMapper;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;
import com.lawu.compensating.transaction.service.TransactionMainService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class BatchTransactionTest {
    
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
    
    @Ignore
    @Test
    public void sendNotice() throws InterruptedException {
        Integer leftCount = 100;
        
        SeckillActivityProductDO seckillActivityProductDO = new SeckillActivityProductDO();
        seckillActivityProductDO.setActivityId(1L);
        seckillActivityProductDO.setAttentionCount(0);
        seckillActivityProductDO.setAuditorAccount("admin");
        seckillActivityProductDO.setAuditTime(new Date());
        seckillActivityProductDO.setGmtCreate(new Date());
        seckillActivityProductDO.setGmtModified(new Date());
        seckillActivityProductDO.setLeftCount(leftCount);
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
        
        int orderQuantity = 100;
        List<Long> shoppingOrderIds = new ArrayList<Long>();
        for (int i = 0; i < orderQuantity; i++) {
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
            
            shoppingOrderIds.add(expected.getId());
        }
        
        //等待事务执行完成
        countDownLatch.await(5000, TimeUnit.MILLISECONDS);
        
        // 成功的订单数量
        long successOrderNum = 0L;
        // 失败的订单数量
        long failOrderNum = 0L;
        for (Long shoppingOrderId : shoppingOrderIds) {
            // 发送消息是否正常
            TransactionRecordDOExample example = new TransactionRecordDOExample();
            example.createCriteria().andRelateIdEqualTo(shoppingOrderId).andTypeEqualTo(BatchTransactionTest.TYPE);
            TransactionRecordDO transactionRecordDO = transactionRecordDOMapper.selectByExample(example).get(0);
            Assert.assertNotNull(transactionRecordDO);
            Assert.assertNotNull(transactionRecordDO.getGmtModified());
            Assert.assertNotNull(transactionRecordDO.getGmtCreate());
            Assert.assertNotNull(transactionRecordDO.getId());
            Assert.assertNotNull(transactionRecordDO.getType());
            Assert.assertEquals(true, transactionRecordDO.getIsProcessed());
            Assert.assertEquals(shoppingOrderId, transactionRecordDO.getRelateId());
            
            FollowTransactionRecordDOExample followTransactionRecordDOExample = new FollowTransactionRecordDOExample();
            followTransactionRecordDOExample.createCriteria().andTransationIdEqualTo(transactionRecordDO.getId()).andTopicEqualTo(BatchTransactionTest.TOPIC);
            Long count = followTransactionRecordDOMapper.countByExample(followTransactionRecordDOExample);
            Assert.assertEquals(1L, count.longValue());
            
            ShoppingOrderDO actualShoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(shoppingOrderId);
            Assert.assertNotNull(actualShoppingOrderDO);
            if (actualShoppingOrderDO.getOrderStatus().equals(Byte.valueOf((byte)0x01))) {
                successOrderNum++;
            } else if (actualShoppingOrderDO.getOrderStatus().equals(Byte.valueOf((byte)0x05))) {
                failOrderNum++;
            }
        }
        SeckillActivityProductDO actualSeckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(seckillActivityProductDO.getId());
        Assert.assertNotNull(actualSeckillActivityProductDO);
        Assert.assertEquals(seckillActivityProductDO.getLeftCount() - successOrderNum, actualSeckillActivityProductDO.getLeftCount().intValue());
        Assert.assertEquals(orderQuantity - successOrderNum, failOrderNum);
    }
}

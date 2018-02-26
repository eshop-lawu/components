package com.lawu.concurrentqueue.bizctrl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.concurrentqueue.ApplicationTest;
import com.lawu.concurrentqueue.base.EmbeddedRedis;
import com.lawu.concurrentqueue.base.Result;
import com.lawu.concurrentqueue.domain.SeckillActivityProductDO;
import com.lawu.concurrentqueue.mapper.SeckillActivityProductDOMapper;

/**
 * @author Leach
 * @date 2017/11/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class BusinessDecisionAspectTest extends EmbeddedRedis {
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Autowired
    private OrderService orderService;
    
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    
    @SuppressWarnings("rawtypes")
    @Test
    public void aroundMethod() throws InterruptedException {
        SeckillActivityProductDO seckillActivityProductDO = new SeckillActivityProductDO();
        seckillActivityProductDO.setActivityId(1L);
        seckillActivityProductDO.setAttentionCount(0);
        seckillActivityProductDO.setAuditorAccount("admin");
        seckillActivityProductDO.setAuditTime(new Date());
        seckillActivityProductDO.setGmtCreate(new Date());
        seckillActivityProductDO.setGmtModified(new Date());
        seckillActivityProductDO.setLeftCount(2);
        seckillActivityProductDO.setMerchantId(1L);
        seckillActivityProductDO.setOriginalPrice(new BigDecimal(0));
        seckillActivityProductDO.setProductId(1L);
        seckillActivityProductDO.setProductModelCount(2);
        seckillActivityProductDO.setProductName("test");
        seckillActivityProductDO.setProductPicture("test.png");
        seckillActivityProductDO.setStatus((byte)0x01);
        seckillActivityProductDO.setTurnover(new BigDecimal(0));
        seckillActivityProductDO.setReasons("");
        seckillActivityProductDOMapper.insert(seckillActivityProductDO);
        
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger selloutCount = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Result result = orderService.createOrder(seckillActivityProductDO.getId());
                switch (result.getRet()) {
                    case 1:
                        successCount.incrementAndGet();
                        break;
                    case 2:
                        selloutCount.incrementAndGet();
                        break;
                    default:
                        selloutCount.incrementAndGet();
                        break;
                }
            }).start();
        }
        
        countDownLatch.await(2000, TimeUnit.MILLISECONDS);
        
        Assert.assertEquals(2, successCount.intValue());
        Assert.assertEquals(3, selloutCount.intValue());
        
        SeckillActivityProductDO actual = seckillActivityProductDOMapper.selectByPrimaryKey(seckillActivityProductDO.getId());
        Assert.assertEquals(0, actual.getLeftCount().intValue());
    }

}

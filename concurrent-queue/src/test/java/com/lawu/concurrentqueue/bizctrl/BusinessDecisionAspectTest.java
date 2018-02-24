package com.lawu.concurrentqueue.bizctrl;

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

/**
 * @author Leach
 * @date 2017/11/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class BusinessDecisionAspectTest extends EmbeddedRedis {

    @Autowired
    private OrderService orderService;
    
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void aroundMethod() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger selloutCount = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Result result = orderService.createOrder(1);
                switch (result.getRet()) {
                    case 1:
                        successCount.incrementAndGet();
                        break;
                    case 2:
                        selloutCount.incrementAndGet();
                        break;
                }
            }).start();
        }
        
        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        
        Assert.assertEquals(2, successCount.intValue());
        Assert.assertEquals(3, selloutCount.intValue());
    }

}

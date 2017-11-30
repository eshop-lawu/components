package com.lawu.concurrentqueue.bizctrl;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.concurrentqueue.base.EmbeddedRedis;
import com.lawu.concurrentqueue.base.Result;

/**
 * @author Leach
 * @date 2017/11/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/concurrent-queue-spring-test.xml"})
public class BusinessDecisionAspectTest extends EmbeddedRedis {

    @Autowired
    private OrderService orderService;

    @Test
    public void aroundMethod() {
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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        Assert.assertEquals(2, successCount.intValue());
        Assert.assertEquals(3, selloutCount.intValue());
    }

}

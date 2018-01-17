package com.lawu.mq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.mq.consumer.CustomConsumer;
import com.lawu.mq.consumer.CustomConsumerRegister;
import com.lawu.mq.message.MessageProducerService;

/**
 * @author Leach
 * @date 2017/9/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchProducerTest {
    
    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private CustomConsumerRegister customConsumerRegister;
    
    private CountDownLatch countDownLatch;

    private ExecutorService executorService = new ThreadPoolExecutor(50, 80, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));


    @Ignore
    @Test
    public void sendMessage() throws InterruptedException {
        int messageCount = 100;
        countDownLatch = new CountDownLatch(messageCount * 3);

        AtomicInteger sendCount = new AtomicInteger(0);
        AtomicInteger revCount = new AtomicInteger(0);
        AtomicInteger revReplyCount = new AtomicInteger(0);

        customConsumerRegister.registerConsumers(new CustomConsumer("test_topic", "test_tag") {
            @Override
            public void consumeMessage(Object message, long storeTimestamp) {
                int currentCount = revCount.incrementAndGet();
                System.out.println("------rev------:" + currentCount + "," + message.toString() + "（" + (System.currentTimeMillis() - storeTimestamp) + "）");
                messageProducerService.sendMessage("test_topic_reply", "test_tag", message + "-reply");
                //System.out.println("******send-reply******:" + message);
                countDownLatch.countDown();
            }
        });
        customConsumerRegister.registerConsumers(new CustomConsumer("test_topic_reply", "test_tag") {
            @Override
            public void consumeMessage(Object message, long storeTimestamp) {
                int currentCount = revReplyCount.incrementAndGet();
                System.out.println("------rev-reply------:" + currentCount + "," + message.toString() + "（" + (System.currentTimeMillis() - storeTimestamp) + "）");
                countDownLatch.countDown();
            }
        });
        for (int i = 0; i < messageCount; i++) {
            int index = i;
            new Thread(() -> {
                messageProducerService.sendMessage("test_topic", "test_tag", "message-" + index);
                int currentCount = sendCount.incrementAndGet();
                System.out.println("******send******:"  + currentCount + "," +  index);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.printf("sendCount:%s, revCount:%s, revReplyCount:%s\n", sendCount, revCount, revReplyCount);
    }
}

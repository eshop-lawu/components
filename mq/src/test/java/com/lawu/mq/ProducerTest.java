package com.lawu.mq;

import java.util.concurrent.CountDownLatch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.mq.message.MessageProducerService;

/**
 * @author Leach
 * @date 2017/9/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {
    
    @Autowired
    private MessageProducerService messageProducerService;
    
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    
    @Ignore
    @Test
    public void tt() throws InterruptedException {
        messageProducerService.sendMessage("test_topic", "test_tag", "this is a message1");
        countDownLatch.await();
    }
}

package com.lawu.framework.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年2月28日
 * @updateDate 2018年2月28日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class EventTest {
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Test
    public void asyncEvent() {
        eventPublisher.publishTestAsyncEvent();
    }
    
    @Test
    public void syncEvent() {
        eventPublisher.publishTestSyncEvent();
    }
}

package com.lawu.framework.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年2月28日
 * @updateDate 2018年2月28日
 */
@Component
public class EventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    public void publishTestAsyncEvent() {
        TestAsyncEvent event = new TestAsyncEvent(this);
        applicationContext.publishEvent(event);
    }
    
    public void publishTestSyncEvent() {
        TestSyncEvent event = new TestSyncEvent(this);
        applicationContext.publishEvent(event);
    }
}

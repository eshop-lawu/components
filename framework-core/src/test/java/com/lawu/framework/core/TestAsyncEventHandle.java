package com.lawu.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lawu.framework.core.event.AsyncEventHandle;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年1月4日
 * @updateDate 2018年1月4日
 */
@Component
public class TestAsyncEventHandle implements AsyncEventHandle<TestAsyncEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(TestAsyncEventHandle.class);

    @Override
    public void execute(TestAsyncEvent event) {
        log.info("into async event handle");
    }
    
}

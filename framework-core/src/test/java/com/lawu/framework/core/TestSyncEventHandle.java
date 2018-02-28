package com.lawu.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lawu.framework.core.event.SyncEventHandle;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年1月4日
 * @updateDate 2018年1月4日
 */
@Component
public class TestSyncEventHandle implements SyncEventHandle<TestSyncEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(TestSyncEventHandle.class);

    @Override
    public void execute(TestSyncEvent event) {
        log.info("into sync event handle");
    }
    
}

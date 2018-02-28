package com.lawu.framework.core;

import com.lawu.framework.core.event.SyncEvent;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年1月4日
 * @updateDate 2018年1月4日
 */
public class TestSyncEvent extends SyncEvent {

    private static final long serialVersionUID = -2803285901780541750L;
    
    public TestSyncEvent(Object source) {
        super(source);
    }

}

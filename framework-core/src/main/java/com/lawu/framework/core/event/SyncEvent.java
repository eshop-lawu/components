package com.lawu.framework.core.event;

import org.springframework.context.ApplicationEvent;

/**
 *
 * 同步事件抽象类
 * @author Leach
 * @date 2017/6/29
 */
public abstract class SyncEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SyncEvent(Object source) {
        super(source);
    }
}

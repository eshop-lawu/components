package com.lawu.framework.core.event;

import org.springframework.context.ApplicationEvent;

/**
 *
 * 异步事件抽象类
 * @author Leach
 * @date 2017/6/29
 */
public abstract class AsyncEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AsyncEvent(Object source) {
        super(source);
    }
}

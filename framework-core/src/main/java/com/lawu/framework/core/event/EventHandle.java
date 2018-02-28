package com.lawu.framework.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Leach
 * @date 2017/6/29
 */
public interface EventHandle<T extends ApplicationEvent> {

    void execute(T event);
}

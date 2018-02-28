package com.lawu.framework.core.event;

import org.springframework.scheduling.annotation.Async;

/**
 *
 * 异步事件监听器
 * @author Leach
 * @date 2017/6/29
 */
public class AsyncEventApplicationListener extends AbstractEventApplicationListener<AsyncEventHandle, AsyncEvent> {

    @Async
    @Override
    public void onApplicationEvent(AsyncEvent event) {
        execute(event);
    }

    @Override
    protected Class getEventHandleClass() {
        return AsyncEventHandle.class;
    }
}

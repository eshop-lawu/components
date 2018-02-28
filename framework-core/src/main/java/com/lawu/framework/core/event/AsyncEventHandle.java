package com.lawu.framework.core.event;

/**
 *
 * 异步事件处理器
 * @author Leach
 * @date 2017/6/29
 */
public interface AsyncEventHandle<T extends AsyncEvent> extends EventHandle<T> {

    void execute(T event);
}

package com.lawu.framework.core.event;

/**
 *
 * 同步事件处理器
 * @author Leach
 * @date 2017/6/29
 */
public interface SyncEventHandle<T extends SyncEvent> extends EventHandle<T> {

    void execute(T event);
}

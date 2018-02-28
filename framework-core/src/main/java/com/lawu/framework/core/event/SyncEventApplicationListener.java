package com.lawu.framework.core.event;

/**
 *
 * 同步事件监听器
 * @author Leach
 * @date 2017/6/29
 */
public class SyncEventApplicationListener extends AbstractEventApplicationListener<SyncEventHandle, SyncEvent> {

    @Override
    public void onApplicationEvent(SyncEvent event) {
        execute(event);
    }

    @Override
    protected Class getEventHandleClass() {
        return SyncEventHandle.class;
    }
}

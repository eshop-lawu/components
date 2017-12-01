package com.lawu.concurrentqueue.bizctrl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Leach
 * @date 2017/11/30
 */
public abstract class AbstractBusinessDecisionService<T> implements BusinessDecisionService<T> {

    /**
     * 一个类别的同步对象map，每个id对应一个对象
     */
    private ConcurrentMap<Object, Object> synObjects = new ConcurrentHashMap<>();

    @Override
    public Object getSynObj(Object id) {
        // 1、直接通过get获取已有同步对象
        if (synObjects.get(id) == null) {
            Object previousSynObj = synObjects.putIfAbsent(id, new Object());
            if (previousSynObj != null) {
                return previousSynObj;
            }
        }

        // 2、再次通过get获取已存同步对象，此处不能与1合并，否则可能出现null
        return synObjects.get(id);
    }
}

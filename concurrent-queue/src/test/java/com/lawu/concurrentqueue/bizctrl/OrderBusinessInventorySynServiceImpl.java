package com.lawu.concurrentqueue.bizctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.cache.InventoryCacheManager;

/**
 * @author Leach
 * @date 2017/11/29
 */
@Service
public class OrderBusinessInventorySynServiceImpl extends AbstractBusinessInventorySynService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    Integer getInventoryFromCache(String businessKey, Object id) {
        return InventoryCacheManager.getInventoryFromCache(stringRedisTemplate, "", businessKey, id);
    }

    @Override
    void setInventoryToCache(String businessKey, Object id, Integer inventory) {
        InventoryCacheManager.setInventoryToCache(stringRedisTemplate, "", businessKey, id, inventory);
    }

    @Override
    Integer decreaseInventoryToCache(String businessKey, Object id) {
        return InventoryCacheManager.decreaseInventoryToCache(stringRedisTemplate, "", businessKey, id);
    }

    @Override
    void increaseInventoryToCache(String businessKey, Object id) {
        InventoryCacheManager.increaseInventoryToCache(stringRedisTemplate, "", businessKey, id);
    }
}

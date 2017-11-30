package com.lawu.concurrentqueue.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * @author Leach
 * @date 2017/11/29
 */
public class InventoryCacheManager {


    private static String formateKey(String keyPrefix, String businessKey, Object id) {
        return keyPrefix + businessKey + "_" + id;
    }

    /**
     * 从缓存中获取剩余库存
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @return
     */
    public static Integer getInventoryFromCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {
        String value = stringRedisTemplate.opsForValue().get(formateKey(keyPrefix, businessKey, id));
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 更新缓存中的库存
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @param inventory
     */
    public static void setInventoryToCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id, Integer inventory) {
        stringRedisTemplate.opsForValue().setIfAbsent(formateKey(keyPrefix, businessKey, id), String.valueOf(inventory));
    }

    /**
     * 缓存中的库存量减一
     * @param stringRedisTemplate
     * @param keyPrefix
     * @param businessKey
     * @param id
     * @return 减完之后的值
     */
    public static Integer decreaseInventoryToCache(StringRedisTemplate stringRedisTemplate, String keyPrefix, String businessKey, Object id) {

        Long count = stringRedisTemplate.boundValueOps(formateKey(keyPrefix, businessKey, id)).increment(-1);

        return count.intValue();
    }
}

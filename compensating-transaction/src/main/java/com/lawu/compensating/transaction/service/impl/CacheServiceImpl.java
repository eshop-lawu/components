package com.lawu.compensating.transaction.service.impl;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.compensating.transaction.service.CacheService;

public class CacheServiceImpl implements CacheService {
    
    /**
     * 事务定时任务执行次数
     */
    public static final String REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX = "TRANSACTION_EXECUTION_COUNT_";
    
    @Autowired(required = false)
    private RedissonClient redissonClient;
    
    @Override
    public Long getCount(String type) {
        return redissonClient.getAtomicLong(REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX.concat(type)).get();
    }

    @Override
    public void addCount(String type) {
        redissonClient.getAtomicLong(REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX.concat(type)).incrementAndGet();
    }

}

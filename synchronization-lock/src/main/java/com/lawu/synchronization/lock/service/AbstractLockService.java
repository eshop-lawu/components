package com.lawu.synchronization.lock.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.synchronization.lock.properties.RedissonProperties;

/**
 * 分布式事务锁接口实现抽象类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月20日
 * @updateDate 2017年12月20日
 */
public abstract class AbstractLockService implements LockService {

    private static Logger logger = LoggerFactory.getLogger(AbstractLockService.class);

    /**
     * 分布式事务同步锁前缀
     */
    public static final String DISTRIBUTED_SYNCHRONIZATION_LOCK = "SYN_LOCK_";

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Autowired
    private RedissonProperties properties;

    /**
     * 判断锁是否存在 如果存在返回false 如果不存在，生成一个锁，并且返回true
     * 
     * @param lockKey
     *            锁的名称
     * @return
     * @author Sunny
     * @date 2017年5月31日
     */
    @Override
    public boolean tryLock(String lockKey) {
        RedissonProperties.Lock lock = properties.getLock();
        boolean rtn = false;
        RLock rLock = getLock(lockKey);
        try {
            rtn = rLock.tryLock(lock.getWaitTime(), lock.getLeaseTime(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("获取锁失败", e);
        }
        return rtn;
    }

    /**
     * 释放锁
     * 
     * @param lockKey
     *            锁的名称
     * @author Sunny
     * @date 2017年5月31日
     */
    @Override
    public void unLock(String lockKey) {
        RLock rLock = getLock(lockKey);
        try {
            rLock.unlock();
        } catch (IllegalMonitorStateException e) {
            logger.error("释放锁失败", e);
        }
    }

    /**
     * 获取锁
     * 
     * @param lockKey
     *            锁的名称
     * @return
     * @author Sunny
     * @date 2017年5月31日
     */
    private RLock getLock(String lockKey) {
        String key = DISTRIBUTED_SYNCHRONIZATION_LOCK.concat(lockKey);
        return redissonClient.getLock(key);
    }
}

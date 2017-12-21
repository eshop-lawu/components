package com.lawu.synchronization.lock;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.synchronization.lock.service.LockService;

/**
 * 分布式事务锁接口测试类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月20日
 * @updateDate 2017年12月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class LockServiceImplTest extends EmbeddedRedis {
    
    @Autowired
    private LockService lockService;
    
    @Test
    public void tryLock() {
        String lockName = "test";
        boolean result = lockService.tryLock(lockName);
        assertTrue(result);
        lockService.unLock(lockName);
        result = lockService.tryLock(lockName);
        assertTrue(result);
    }
}

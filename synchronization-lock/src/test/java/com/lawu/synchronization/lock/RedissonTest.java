package com.lawu.synchronization.lock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.synchronization.lock.service.LockService;

/**
 * @author Leach
 * @date 2017/6/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class RedissonTest {
    
    private static Logger logger = LoggerFactory.getLogger(RedissonTest.class);
    
    @Autowired
    private LockService lockService;
    
    private List<Future<Integer>> result = new ArrayList<>();

    private ExecutorService executorService = new ThreadPoolExecutor(3000, 3500, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        private int counter = 0;
        
        @Override
        public Thread newThread(Runnable r) {
            counter++;
            return new Thread(r, "RedissonTest-Thread-" + counter);
        }
    });
    
    @Ignore
    @Test
    public void lock() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
        	String key = "order_srv_create_order" + i;
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    try {
                    	if (!lockService.tryLock(key)) {
                    		System.out.println("锁还未释放");
                    		return 0;
                    	}
                    	lockService.unLock(key);
                    	logger.info(key);
                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            result.add(future);
        }
        for (Future<Integer> item : result) {
            logger.info(item.get().toString());
        }
        System.out.printf("Total time: %s", System.currentTimeMillis() - startTime);
    }

}

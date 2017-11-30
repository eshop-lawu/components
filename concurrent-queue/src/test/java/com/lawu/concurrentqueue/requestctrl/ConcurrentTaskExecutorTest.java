package com.lawu.concurrentqueue.requestctrl;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.concurrentqueue.base.Result;

/**
 * @author Leach
 * @date 2017/11/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/concurrent-queue-spring-test.xml"})
public class ConcurrentTaskExecutorTest {

    @Autowired
    private ConcurrentTaskExecutor concurrentTaskExecutor;

    @Test
    public void execute() {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger rejectedCount = new AtomicInteger(0);
        AtomicInteger exceptionCount = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            int index = i;
            new Thread(() -> {
                Result result = executeResult(index);
                switch (result.getRet()) {
                    case 1:
                        successCount.incrementAndGet();
                        break;
                    case 2:
                        rejectedCount.incrementAndGet();
                        break;
                    case 3:
                        exceptionCount.incrementAndGet();
                        break;
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        System.out.printf("successCount:%s, rejectedCount:%s, exceptionCount:%s, ", successCount.intValue(), rejectedCount.intValue(), exceptionCount.intValue());
        Assert.assertEquals(6, successCount.intValue());
        Assert.assertEquals(3, rejectedCount.intValue());
        Assert.assertEquals(1, exceptionCount.intValue());
    }

    private Result executeResult(int index) {
        return (Result) concurrentTaskExecutor.execute(new ConcurrentTask<Result, String>() {

            @Override
            public String execute() {
                executeMock(index);
                return "success";
            }

            @Override
            public Result executeWhenSuccess(String successInfo) {
                Result rs = new Result();
                rs.setRet(1);
                rs.setModel(successInfo);
                return rs;
            }

            @Override
            public Result executeWhenRejected() {
                Result rs = new Result();
                rs.setRet(2);
                return rs;
            }

            @Override
            public Result executeWhenException() {
                Result rs = new Result();
                rs.setRet(3);
                return rs;
            }
        });
    }

    private synchronized void executeMock(int index) {

        if (index == 2) {
            throw new RuntimeException();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

}

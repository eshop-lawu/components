package com.lawu.concurrentqueue.requestctrl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Leach
 * @date 2017/11/27
 */
public class ConcurrentTaskExecutor implements InitializingBean {

    private int corePoolSize;

    private int maximumPoolSize;

    private long keepAliveTime;

    private int queueCount;

    private BlockingQueue<Runnable> workQueue;

    private ExecutorService executorService;

    public Object execute(ConcurrentTask concurrentTask) {

        try {

            Callable thread = concurrentTask.createThread();
            Future<Object> future = executorService.submit(thread);
            Object result = future.get();
            return concurrentTask.executeWhenSuccess(result);

        } catch (RejectedExecutionException e) {
            return concurrentTask.executeWhenRejected();
        } catch (InterruptedException | ExecutionException e) {
            return concurrentTask.executeWhenException();
        }
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        workQueue = new LinkedBlockingQueue<>(queueCount);
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, new ConcurrentQueueThreadFactory("ConcurrentQueueThread_"));

    }
}

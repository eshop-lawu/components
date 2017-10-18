package com.lawu.service.monitor.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lawu.service.monitor.handle.HttpCheckHandle;

/**
 * 定时任务管理
 * @author Leach
 * @date 2017/10/17
 */
@WebListener
public class TaskManager implements ServletContextListener {

    private static final int CORE_POOL_SIZE = 1;

    /**
     * 定时器
     */
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);

    /**
     * 在Web应用启动时初始化任务
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        HttpCheckHandle httpCheckHandle = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()).getBean(HttpCheckHandle.class);
        executorService.scheduleAtFixedRate(new CheckTask(httpCheckHandle), 0, 1, TimeUnit.SECONDS);

    }

    /**
     * 在Web应用结束时停止任务
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        executorService.shutdown();
    }
}
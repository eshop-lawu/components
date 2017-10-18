package com.lawu.service.monitor.task;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lawu.service.monitor.handle.HttpCheckHandle;

/**
 * @author Leach
 * @date 2017/10/17
 */
public class CheckTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(CheckTask.class);

    private static boolean isRunning = false;

    private HttpCheckHandle httpCheckHandle;

    public CheckTask(HttpCheckHandle httpCheckHandle) {
        this.httpCheckHandle = httpCheckHandle;
    }

    @Override
    public void run() {
        if (!isRunning) {
            isRunning = true;
            try {
                logger.debug("-----------API检测任务启动 -----------");
                httpCheckHandle.checkBatch();
            } catch (Exception e) {
                logger.error("-----------API检测任务 err -----------", e);
            }
            isRunning = false;
        } else {
            logger.warn("上一次任务执行还未结束");
        }
    }
}

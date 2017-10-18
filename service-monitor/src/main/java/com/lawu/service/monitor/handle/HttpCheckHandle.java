package com.lawu.service.monitor.handle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lawu.service.monitor.cache.ResultCache;
import com.lawu.service.monitor.config.CheckConfig;
import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.NoticeConfigs;
import com.lawu.service.monitor.handle.api.HttpRequestHandle;
import com.lawu.service.monitor.handle.api.HttpRequestHandleFactory;
import com.lawu.service.monitor.handle.notice.NoticeHandle;
import com.lawu.service.monitor.result.CheckResult;

/**
 * @author Leach
 * @date 2017/10/17
 */
@Service
public class HttpCheckHandle {

    private Logger logger = LoggerFactory.getLogger(HttpCheckHandle.class);

    @Autowired
    private HttpRequestHandleFactory httpRequestHandleFactory;

    @Autowired
    private CheckConfig checkConfig;

    @Autowired
    @Qualifier("dingtalkNoticeHandle")
    private NoticeHandle dingTalkNoticeHandle;

    private int checkTimes;

    private ExecutorService executorService = new ThreadPoolExecutor(100, 200, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<>(300));

    public void checkBatch() {
        List<HttpRequestConfig> requestInfoList = checkConfig.getRequests();

        for (int i = 0; i < requestInfoList.size(); i++) {
            HttpRequestConfig httpRequestConfig = requestInfoList.get(i);
            if (checkTimes % httpRequestConfig.getCheckPeriod() == 0) {
                executorService.submit(() -> {

                    HttpRequestHandle handle = httpRequestHandleFactory.getHandle(httpRequestConfig.getType());
                    CheckResult checkResult = handle.execute(httpRequestConfig);
                    String url = httpRequestConfig.getUrl();

                    if (checkResult.isSuccess(httpRequestConfig)) {
                        logger.info("{} Check result: success ", httpRequestConfig.getTitle());
                        if (ResultCache.getFailTimes(url) > 0) {
                            ResultCache.clearFailTimes(url);
                            sendNotice(checkResult, httpRequestConfig);
                        }
                    } else {
                        int failTimes = ResultCache.increaseFailTimes(url);
                        logger.info("{} Check result: fail ", httpRequestConfig.getTitle());
                        logger.info("Check result: fail ({})", failTimes);
                        if (failTimes % httpRequestConfig.getNoticeAgain() == 1) {
                            sendNotice(checkResult, httpRequestConfig);
                        }
                    }
                });
            }

        }
        checkTimes++;
    }

    private void sendNotice(CheckResult checkResult, HttpRequestConfig httpRequestConfig) {
        NoticeConfigs notices = checkConfig.getNotices();
        if (notices.getDingtalk() != null) {
            dingTalkNoticeHandle.send(checkResult, httpRequestConfig);
        }
    }

}

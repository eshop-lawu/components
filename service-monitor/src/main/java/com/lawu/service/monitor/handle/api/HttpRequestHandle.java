package com.lawu.service.monitor.handle.api;

import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.result.CheckResult;

/**
 * @author Leach
 * @date 2017/10/16
 */
public interface HttpRequestHandle {

    /**
     * 根据提供的请求信息进行检测
     */
    CheckResult execute(HttpRequestConfig httpRequestConfig);
}

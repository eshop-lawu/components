package com.lawu.autotest.tool.handle.api;

import com.lawu.autotest.client.config.api.HttpRequestConfig;
import com.lawu.autotest.tool.result.CheckResult;

/**
 * @author meishuquan
 * @date 2017/10/25
 */
public interface HttpRequestHandle {

    /**
     * 根据提供的请求信息进行检测
     */
    CheckResult execute(HttpRequestConfig httpRequestConfig);
}

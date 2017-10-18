package com.lawu.service.monitor.handle.notice;

import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.NoticeConfig;
import com.lawu.service.monitor.result.CheckResult;

/**
 * @author Leach
 * @date 2017/10/16
 */
public interface NoticeHandle<T extends NoticeConfig> {

    T getNoticeConfig();

    void send(CheckResult checkResult, HttpRequestConfig httpRequestConfig);
}

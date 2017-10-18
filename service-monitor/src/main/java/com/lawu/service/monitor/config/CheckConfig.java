package com.lawu.service.monitor.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.NoticeConfigs;

/**
 * @author Leach
 * @date 2017/10/17
 */
@Component
@ConfigurationProperties(prefix = "checkConfig")
public class CheckConfig {

    private List<HttpRequestConfig> requests = new ArrayList<>();

    private NoticeConfigs notices = new NoticeConfigs();

    public List<HttpRequestConfig> getRequests() {
        return requests;
    }

    public void setRequests(List<HttpRequestConfig> requests) {
        this.requests = requests;
    }

    public NoticeConfigs getNotices() {
        return notices;
    }

    public void setNotices(NoticeConfigs notices) {
        this.notices = notices;
    }
}

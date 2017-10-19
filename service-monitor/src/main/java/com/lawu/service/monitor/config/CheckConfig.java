package com.lawu.service.monitor.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.NoticeConfigs;

/**
 * @author Leach
 * @date 2017/10/17
 */
@Component
public class CheckConfig implements InitializingBean{

    @Value(value="/config.json")
    private Resource configResource;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        StringBuffer json = new StringBuffer("");
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(configResource.getFile()), "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                json.append(line.trim().replaceAll("(?<!:)\\/\\/.*|\\/\\*(\\s|.)*?\\*\\/", "") + "\r\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
        CheckConfig checkConfig = JSON.parseObject(json.toString(), this.getClass());
        this.setNotices(checkConfig.getNotices());
        this.setRequests(checkConfig.getRequests());
    }
}

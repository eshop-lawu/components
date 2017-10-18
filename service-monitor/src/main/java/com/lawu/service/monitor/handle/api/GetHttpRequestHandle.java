package com.lawu.service.monitor.handle.api;


import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lawu.service.monitor.config.api.HttpHeaderConfig;
import com.lawu.service.monitor.config.api.HttpParamConfig;
import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.result.CheckResult;

/**
 * @author Leach
 * @date 2017/10/16
 */
@Service("getHttpRequestHandle")
public class GetHttpRequestHandle implements HttpRequestHandle {

    private Logger logger = LoggerFactory.getLogger(GetHttpRequestHandle.class);

    private HttpClient httpclient = HttpClients.createDefault();

    @Override
    public CheckResult execute(HttpRequestConfig httpRequestConfig) {

        StringBuilder url = new StringBuilder(httpRequestConfig.getUrl());

        if (!httpRequestConfig.getParams().isEmpty()) {
            url.append("?");
            for (HttpParamConfig httpParamConfig : httpRequestConfig.getParams()) {
                url.append(httpParamConfig.getName());
                url.append("=");
                url.append(httpParamConfig.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(url.toString());

        for (HttpHeaderConfig httpHeaderConfig : httpRequestConfig.getHeaders()) {
            httpGet.addHeader(httpHeaderConfig.getName(), httpHeaderConfig.getValue());
        }


        CheckResult checkResult = new CheckResult();
        try {
            long requestTime = System.currentTimeMillis();
            checkResult.setRequestTime(new Date(requestTime));
            HttpResponse response = this.httpclient.execute(httpGet);
            checkResult.setResponseTime(System.currentTimeMillis() - requestTime);

            checkResult.setResponseCode(response.getStatusLine().getStatusCode());
            checkResult.setResponseMsg(EntityUtils.toString(response.getEntity(), "utf-8"));

        } catch (IOException e) {
            logger.info("Check-get fail", e.getMessage());
            checkResult.setResponseMsg(e.getMessage());
        }

        return checkResult;
    }
}

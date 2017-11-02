package com.lawu.autotest.tool.handle.api;


import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lawu.autotest.client.config.api.HttpHeaderConfig;
import com.lawu.autotest.client.config.api.HttpParamConfig;
import com.lawu.autotest.client.config.api.HttpRequestConfig;
import com.lawu.autotest.tool.result.CheckResult;


/**
 * @author meishuquan
 * @date 2017/10/25
 */
@Service("postHttpRequestHandle")
public class PostHttpRequestHandle implements HttpRequestHandle {

    private Logger logger = LoggerFactory.getLogger(PostHttpRequestHandle.class);

    private HttpClient httpclient = HttpClients.createDefault();

    @Override
    public CheckResult execute(HttpRequestConfig httpRequestConfig) {

        StringBuilder url = new StringBuilder(httpRequestConfig.getUrl());
        String entity = "";

        if (!httpRequestConfig.getParams().isEmpty()) {
            url.append("?");
            for (HttpParamConfig httpParamConfig : httpRequestConfig.getParams()) {
                if (httpParamConfig.getName() == null) {
                    entity = httpParamConfig.getValue();
                } else {
                    url.append(httpParamConfig.getName());
                    url.append("=");
                    url.append(httpParamConfig.getValue());
                    url.append("&");
                }
            }
        }

        HttpPost httpPost = new HttpPost(url.toString());
        httpPost.addHeader("authorization","100");

        for (HttpHeaderConfig httpHeaderConfig : httpRequestConfig.getHeaders()) {
            httpPost.addHeader(httpHeaderConfig.getName(), httpHeaderConfig.getValue());
        }

        CheckResult checkResult = new CheckResult();
        try {
            if (StringUtils.isNotEmpty(entity)) {
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(entity));
            }

            HttpResponse response = this.httpclient.execute(httpPost);
            checkResult.setResponseCode(response.getStatusLine().getStatusCode());
            checkResult.setResponseMsg(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            logger.info("Check-post fail", e.getMessage());
            checkResult.setResponseMsg(e.getMessage());
        }

        return checkResult;
    }
}

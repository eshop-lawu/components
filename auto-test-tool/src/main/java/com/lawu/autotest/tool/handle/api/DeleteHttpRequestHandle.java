package com.lawu.autotest.tool.handle.api;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
@Service("deleteHttpRequestHandle")
public class DeleteHttpRequestHandle implements HttpRequestHandle {

    private Logger logger = LoggerFactory.getLogger(DeleteHttpRequestHandle.class);

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
                url.append("&");
            }
        }

        HttpDelete httpDelete = new HttpDelete(url.toString());

        for (HttpHeaderConfig httpHeaderConfig : httpRequestConfig.getHeaders()) {
            httpDelete.addHeader(httpHeaderConfig.getName(), httpHeaderConfig.getValue());
        }

        CheckResult checkResult = new CheckResult();
        try {
            HttpResponse response = this.httpclient.execute(httpDelete);
            checkResult.setResponseCode(response.getStatusLine().getStatusCode());
            checkResult.setResponseMsg(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            logger.info("Check-delete fail", e.getMessage());
            checkResult.setResponseMsg(e.getMessage());
        }

        return checkResult;
    }
}

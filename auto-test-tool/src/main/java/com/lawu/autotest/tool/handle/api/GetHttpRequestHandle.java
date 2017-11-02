package com.lawu.autotest.tool.handle.api;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lawu.autotest.client.config.api.HttpHeaderConfig;
import com.lawu.autotest.client.config.api.HttpParamConfig;
import com.lawu.autotest.client.config.api.HttpRequestConfig;
import com.lawu.autotest.client.util.AnnotationScanUtil;
import com.lawu.autotest.tool.result.CheckResult;


/**
 * @author meishuquan
 * @date 2017/10/25
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
                url.append("&");
            }
        }

        HttpGet httpGet = new HttpGet(url.toString());
        httpGet.addHeader("authorization","100");

        for (HttpHeaderConfig httpHeaderConfig : httpRequestConfig.getHeaders()) {
            httpGet.addHeader(httpHeaderConfig.getName(), httpHeaderConfig.getValue());
        }

        CheckResult checkResult = new CheckResult();
        try {
            HttpResponse response = this.httpclient.execute(httpGet);
            checkResult.setResponseCode(response.getStatusLine().getStatusCode());
            checkResult.setResponseMsg(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            logger.info("Check-get fail", e.getMessage());
            checkResult.setResponseMsg(e.getMessage());
        }

        return checkResult;
    }

    public CheckResult getScanInterfaceInfo(String scanPackageName, String requestIpAddress) {
        HttpRequestConfig requestConfig = new HttpRequestConfig();
        requestConfig.setUrl(requestIpAddress + "autoTest/scanInterfaceInfo?packageName=" + scanPackageName + "&requestIpAddress=" + requestIpAddress);
        requestConfig.setMethod("GET");
        requestConfig.setHeaders(AnnotationScanUtil.headerConfigs);
        requestConfig.setParams(new ArrayList<>());
        return execute(requestConfig);
    }
}

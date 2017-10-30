package com.lawu.autotest.client.config.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/10/25
 */
public class HttpRequestConfig {
    private String url;
    private String method;
    private List<HttpHeaderConfig> headers = new ArrayList<>();
    private List<HttpParamConfig> params = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HttpHeaderConfig> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeaderConfig> headers) {
        this.headers = headers;
    }

    public List<HttpParamConfig> getParams() {
        return params;
    }

    public void setParams(List<HttpParamConfig> params) {
        this.params = params;
    }

}

package com.lawu.service.monitor.config.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leach
 * @date 2017/10/16
 */
public class HttpRequestConfig {
    private String title;
    private String url;
    private RequestType type;
    private List<HttpHeaderConfig> headers = new ArrayList<>();
    private List<HttpParamConfig> params = new ArrayList<>();

    /**
     * 正常http状态码
     */
    private int responseCode;

    /**
     * 正常响应时间最大值（毫秒）
     */
    private long responseTime;
    /**
     * 检查周期（秒）
     */
    private long checkPeriod;

    /**
     * 服务没有恢复时，检查次数达到该值时再次通知
     */
    private int noticeAgain;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
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

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getCheckPeriod() {
        return checkPeriod;
    }

    public void setCheckPeriod(long checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public int getNoticeAgain() {
        return noticeAgain;
    }

    public void setNoticeAgain(int noticeAgain) {
        this.noticeAgain = noticeAgain;
    }
}

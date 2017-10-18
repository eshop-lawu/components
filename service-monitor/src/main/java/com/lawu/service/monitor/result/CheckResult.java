package com.lawu.service.monitor.result;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lawu.service.monitor.config.api.HttpRequestConfig;

/**
 * @author Leach
 * @date 2017/10/16
 */
public class CheckResult {

    private Date requestTime;

    private int responseCode;

    /**
     * 响应时间（秒）
     */
    private long responseTime;

    private String responseMsg;

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
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

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public boolean isSuccess(HttpRequestConfig httpRequestConfig) {
        return httpRequestConfig.getResponseCode() == responseCode && httpRequestConfig.getResponseTime() >= responseTime;
    }

    public NoticeType getNoticeType(HttpRequestConfig httpRequestConfig) {
        if (httpRequestConfig.getResponseCode() != responseCode) {
            return NoticeType.HTTPCODE;
        } else if (httpRequestConfig.getResponseTime() < responseTime) {
            return NoticeType.TIMEOUT;
        }

        return NoticeType.RESUME;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestTime", requestTime)
                .append("responseCode", responseCode)
                .append("responseTime", responseTime)
                .append("responseMsg", responseMsg)
                .toString();
    }
}

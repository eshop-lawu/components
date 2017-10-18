package com.lawu.service.monitor.result;

/**
 * @author Leach
 * @date 2017/10/18
 */
public enum NoticeType {
    RESUME("服务恢复"),
    HTTPCODE("状态码异常"),
    TIMEOUT("响应超时");
    private String label;

    NoticeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

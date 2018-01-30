package com.lawu.weixinapi.dto;

/**
 * @author Leach
 * @date 2018/1/30
 */
public class JsConfigDTO {
    /**
     * 公众号的唯一标识
     */

    private String appId;
    /**
     * 生成签名的时间戳
     */

    private long timestamp;
    /**
     * 生成签名的随机串
     */

    private String nonceStr;
    /**
     * 签名
     */
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

package com.lawu.constants;

/**
 * @author zhangyong
 * @date 2017/12/12.
 */
public class SmsParams {

    private MessageChannelEnum channel;

    /**
     * appId   创建应用时系统分配的唯一标示
     */
    private String appId;

    /**
     * 短信key/accountSid
     */
    private String accessKeyId;

    /**
     * 短信secret/authToken
     */
    private String accessKeySecret;

    /**
     * 短信API产品名称
     */
    private String product;

    /**
     * 短信API产品URL
     */
    private String channelUrl;

    /**
     * 发送手机号
     */
    private String phone;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板ID-
     */
    private String templateId;

    /**
     * 验证码
     */
    private String smsCode;

    /**
     * 版本
     */
    private String version;

    public MessageChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(MessageChannelEnum channel) {
        this.channel = channel;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

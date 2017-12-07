package com.ucpaas.param;

/**
 * @author zhangyong
 * @date 2017/12/7.
 */
public class SmsParam {

    /**
     * @param accountSid 注册云之讯官网，在控制台中即可获取此参数
     * @param authToken  注册云之讯官网，在控制台中即可获取此参数
     * @param appId      创建应用时系统分配的唯一标示，在“应用列表”中可以查询
     * @param templateId 创建短信模板时系统分配的唯一标示，在“短信管理”中可以查询
     * @param mobile     需要下发短信的手机号码,支持国际号码，需要加国家码
     * @param code       验证码
     */
    private String accountSid;
    private String authToken;
    private String appId;
    private String templateId;
    private String mobile;
    private String code;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

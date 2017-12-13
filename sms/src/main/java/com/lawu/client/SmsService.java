package com.lawu.client;

/**
 * @author zhangyong
 * @date 2017/12/12.
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param templateCode 短信模板
     * @return 发送成功/失败响应
     */
    String sendSms(String mobile, String code, String templateCode);
}

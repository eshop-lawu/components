package com.lawu.email.service;

/**
 * @author zhangyong
 * @date 2018/1/22.
 */
public interface SendEmailService {

    /**
     *
     * @param to 发送对象
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 成功true/失败false
     */
    Boolean sendEmail(String to, String subject, String content);
}

package com.lawu.email.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.lawu.email.constants.EmailConstant;
import com.lawu.email.service.SendEmailService;

/**
 * @author zhangyong
 * @date 2018/1/26.
 */
@Service
public class SendEmailServiceImpl implements SendEmailService, InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    private String from;
    private String password;

    private EmailConstant email;
    private Properties properties;

    private static Map<String, String> hostMap = new HashMap<>();

    static {
        // 126
        hostMap.put("smtp.126", "smtp.126.com");
        // qq
        hostMap.put("smtp.qq", "smtp.qq.com");

        // 163
        hostMap.put("smtp.163", "smtp.163.com");

        // sina
        hostMap.put("smtp.sina", "smtp.sina.com.cn");

        // tom
        hostMap.put("smtp.tom", "smtp.tom.com");

        // 263
        hostMap.put("smtp.263", "smtp.263.net");

        // yahoo
        hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");

        // hotmail
        hostMap.put("smtp.hotmail", "smtp.live.com");

        // gmail
        hostMap.put("smtp.gmail", "smtp.gmail.com");
        hostMap.put("smtp.port.gmail", "465");
    }

    private static String getHost(String email) throws Exception {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find()) {
            key = "smtp." + matcher.group(1);
        }
        if (hostMap.containsKey(key)) {
            return hostMap.get(key);
        } else {
            throw new Exception("unSupportEmail");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 构造mail session
        properties = new Properties();
        properties.put("mail.smtp.host", getHost(from));
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        email = new EmailConstant();
        email.setFrom(from);
        email.setPassword(password);
    }

    @Override
    public Boolean sendEmail(String to, String subject, String content) {
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email.getFrom(), email.getPassword());
                    }
                });

        try {
            // 构造MimeMessage 并设定基本的值
            MimeMessage msg = new MimeMessage(session);
            // MimeMessage msg = new MimeMessage();
            msg.setFrom(new InternetAddress(email.getFrom()));
            // msg.addRecipients(Message.RecipientType.TO, address);
            //这个只能是给一个人发送email
            msg.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(to));
            // subject = transferChinese(subject);
            msg.setSubject(subject);

            // 构造Multipart

            Multipart mp = new MimeMultipart();
            // 向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setContent(content, "text/html;charset=utf-8");
            // 向MimeMessage添加（Multipart代表正文）
            mp.addBodyPart(mbpContent);

            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.setSentDate(new Date());
            msg.saveChanges();
            // 发送邮件

            Transport transport = session.getTransport("smtp");
            transport.connect(getHost(email.getFrom()), email.getFrom(), email.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception mex) {
            logger.info("发送邮件失败 to:" + mex.getMessage() + to
                    + " subject=" + subject);
            return false;
        }
        return true;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

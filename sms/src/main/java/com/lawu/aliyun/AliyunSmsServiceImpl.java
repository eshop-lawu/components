package com.lawu.aliyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lawu.client.SmsService;

import net.sf.json.JSONObject;

/**
 * @author zhangyong
 * @date 2017/12/12.
 */
@Service
public class AliyunSmsServiceImpl implements SmsService, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);
    private String accessKeyId;

    private String accessKeySecret;
    /**
     * 短信API产品名称
     */
    private String product;

    /**
     * 短信API产品域名
     */
    private String aliUrl;

    /**
     * 短信签名
     */
    private String signName;

    private IAcsClient client;

    private SendSmsRequest request;

    @Override
    public String sendSms(String mobile, String content, String templateCode) {
        JSONObject smsCode = new JSONObject();
        smsCode.put("code", content);
        request.setPhoneNumbers(mobile);
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(smsCode.toString());
        // 发送短信
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            return JSONObject.fromObject(response).toString();
        } catch (ClientException e) {
            logger.error("发送短信请求失败：{}", e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, aliUrl);
        } catch (ClientException e) {
            logger.error("发送短信获取IAcsClient对象失败：{}", e);
            return;
        }
        request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信签名-可在短信控制台中找到
        client = new DefaultAcsClient(profile);
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAliUrl(String aliUrl) {
        this.aliUrl = aliUrl;
    }


    public void setSignName(String signName) {
        this.signName = signName;
    }
}

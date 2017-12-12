package com.lawu.aliyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lawu.constants.SmsParams;

import net.sf.json.JSONObject;

/**
 * @author zhangyong
 * @date 2017/11/6.
 */
public class AliYunSmsUtil {
    private static Logger logger = LoggerFactory.getLogger(AliYunSmsUtil.class);

    /**
     * 发送短信（阿里大鱼）
     *
     * @param param
     * @return
     */
    public static String sendMessage(SmsParams param) {
        // 初始化client对象
        IAcsClient client = initClient(param.getAccessKeyId(), param.getAccessKeySecret(), param.getProduct(), param.getChannelUrl());
        if (client == null) {
            logger.error("初始化client对象失败");
            return null;
        }
        // 短信模板请求对象
        JSONObject smsCode = new JSONObject();
        smsCode.put("code", param.getSmsCode());
        SendSmsRequest request = getMessageTemple(param.getPhone(), param.getSignName(), param.getTemplateId(), smsCode.toString());
        // 发送短信
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            return JSONObject.fromObject(response).toString();
        } catch (ClientException e) {
            logger.error("发送短信请求失败：{}", e);
            return null;
        }

    }

    /**
     * 获取IAcsClient对象
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param product         短信API产品名称
     * @param aliUrl          短信API产品域名
     * @return
     * @throws ClientException
     */
    private static IAcsClient initClient(String accessKeyId, String accessKeySecret, String product, String aliUrl) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient,暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, aliUrl);
        } catch (ClientException e) {
            logger.error("发送短信获取IAcsClient对象失败：{}", e);
            return null;
        }
        return new DefaultAcsClient(profile);
    }

    /**
     * 获取短信模板
     *
     * @param phone         待发送手机号
     * @param signName      短信签名
     * @param templateCode  短信模板
     * @param templateParam 模板替换内容
     * @return
     */
    private static SendSmsRequest getMessageTemple(String phone, String signName, String templateCode, String templateParam) {
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");

        return request;
    }

}
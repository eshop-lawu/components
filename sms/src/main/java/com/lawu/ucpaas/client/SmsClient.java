package com.lawu.ucpaas.client;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.lawu.constants.SmsParams;
import com.lawu.ucpaas.DateUtil;
import com.lawu.ucpaas.EncryptUtil;
import com.lawu.ucpaas.models.TemplateSMS;


/**
 * @author zhangyong
 */
public class SmsClient extends AbsRestClient {

    private static Logger logger = LoggerFactory.getLogger(SmsClient.class);

    /**
     * 短信发送接口
     * <p>
     * <p>
     * templateSMS
     */
    @Override
    public String templateSMS(SmsParams param) {
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            //MD5加密
            EncryptUtil encryptUtil = new EncryptUtil();
            // 构造请求URL内容
            // 时间戳
            String timestamp = DateUtil.dateToStr(new Date(),
                    DateUtil.DATE_TIME_NO_SLASH);
            String signature = getSignature(param.getAccessKeyId(), param.getAccessKeySecret(), timestamp, encryptUtil);
            String url = getStringBuffer(param.getChannelUrl()).append("/").append(param.getVersion())
                    .append("/Accounts/").append(param.getAccessKeyId())
                    .append("/Messages/templateSMS")
                    .append("?sig=").append(signature).toString();
            TemplateSMS templateSMS = new TemplateSMS();
            templateSMS.setAppId(param.getAppId());
            templateSMS.setTemplateId(param.getTemplateId());
            templateSMS.setTo(param.getPhone());
            templateSMS.setParam(param.getSmsCode());
            Gson gson = new Gson();
            String body = gson.toJson(templateSMS);
            body = "{\"templateSMS\":" + body + "}";
            logger.info(body);
            HttpResponse response = post("application/json", param.getAccessKeyId(), param.getAccessKeySecret(), timestamp, url, httpclient, encryptUtil, body);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }

}

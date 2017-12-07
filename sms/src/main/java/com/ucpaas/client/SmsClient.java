package com.ucpaas.client;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.ucpaas.DateUtil;
import com.ucpaas.EncryptUtil;
import com.ucpaas.models.Callback;
import com.ucpaas.models.TemplateSMS;
import com.ucpaas.param.SmsParam;


/**
 * @author zhangyong
 */
public class SmsClient extends AbsRestClient {

    private static Logger logger = LoggerFactory.getLogger(SmsClient.class);

    @Override
    public String callback(String accountSid, String authToken, String appId,
                           String fromClient, String to, String fromSerNum, String toSerNum) {
        String result = "";
        DefaultHttpClient httpclient = getDefaultHttpClient();
        try {
            //MD5加密
            EncryptUtil encryptUtil = new EncryptUtil();
            // 构造请求URL内容
            // 时间戳
            String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
            String signature = getSignature(accountSid, authToken, timestamp, encryptUtil);
            String url = getStringBuffer().append("/").append(version)
                    .append("/Accounts/").append(accountSid)
                    .append("/Calls/callBack")
                    .append("?sig=").append(signature).toString();
            System.out.println(url);
            Callback callback = new Callback();
            callback.setAppId(appId);
            callback.setFromClient(fromClient);
            callback.setTo(to);
            callback.setFromSerNum(fromSerNum);
            callback.setToSerNum(toSerNum);
            Gson gson = new Gson();
            String body = gson.toJson(callback);
            body = "{\"callback\":" + body + "}";
            logger.info("", body);
            HttpResponse response = post("application/json", accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
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


    /**
     * 短信发送接口
     *
     *
     * templateSMS
     */
    @Override
    public String templateSMS(SmsParam param) {
        String result = "";
        DefaultHttpClient httpclient = getDefaultHttpClient();
        try {
            //MD5加密
            EncryptUtil encryptUtil = new EncryptUtil();
            // 构造请求URL内容
            // 时间戳
            String timestamp = DateUtil.dateToStr(new Date(),
                    DateUtil.DATE_TIME_NO_SLASH);
            String signature = getSignature(param.getAccountSid(), param.getAuthToken(), timestamp, encryptUtil);
            String url = getStringBuffer().append("/").append(version)
                    .append("/Accounts/").append(param.getAccountSid())
                    .append("/Messages/templateSMS")
                    .append("?sig=").append(signature).toString();
            TemplateSMS templateSMS = new TemplateSMS();
            templateSMS.setAppId(param.getAppId());
            templateSMS.setTemplateId(param.getTemplateId());
            templateSMS.setTo(param.getMobile());
            templateSMS.setParam(param.getCode());
            Gson gson = new Gson();
            String body = gson.toJson(templateSMS);
            body = "{\"templateSMS\":" + body + "}";
            logger.info(body);
            HttpResponse response = post("application/json", param.getAccountSid(), param.getAuthToken(), timestamp, url, httpclient, encryptUtil, body);
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

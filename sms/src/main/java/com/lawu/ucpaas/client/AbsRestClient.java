package com.lawu.ucpaas.client;

import java.io.ByteArrayInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lawu.constants.SmsParams;
import com.lawu.ucpaas.EncryptUtil;


public abstract class AbsRestClient {

    //==========================================短信发送接口 API===========================================

    /**
     * 短信发送接口
     * templateSMS
     */
    public abstract String templateSMS(SmsParams param);


    /**
     * 请求地址
     * @return
     */
    public StringBuffer getStringBuffer(String channelUrl) {
        if(!channelUrl.startsWith("https://")){
            return new StringBuffer("https://" + channelUrl);
        }
        return new StringBuffer(channelUrl);
    }

    /**
     * 获取默认的httpclient
     *
     */
    public DefaultHttpClient getDefaultHttpClient() {
        return new DefaultHttpClient();
    }

    /**
     * 签名
     * @param accountSid 注册云之讯官网，在控制台中即可获取此参数
     * @param authToken 账户授权令牌
     * @param timestamp 时间戳
     * @param encryptUtil 加密工具类
     * @return signature 验证参数：MD5（账户Id + 账户授权令牌 + 时间戳），共32位(注:转成大写)
     * @throws Exception
     */
    public String getSignature(String accountSid, String authToken, String timestamp, EncryptUtil encryptUtil) throws Exception {
        String sig = accountSid + authToken + timestamp;
        return encryptUtil.md5Digest(sig);
    }


    /**
     * 发送post请求
     * @param cType：客户端响应接收数据格式：application/xml、application/json
     * @param accountSid 注册云之讯官网，在控制台中即可获取此参数
     * @param authToken
     * @param timestamp 时间戳
     * @param url 请求地址
     * @param httpclient http客户端
     * @param encryptUtil 加密工具类
     * @param body 请求参数
     * @return response 请求响应
     * @throws Exception
     */
    public HttpResponse post(String cType, String accountSid, String authToken, String timestamp, String url, DefaultHttpClient httpclient, EncryptUtil encryptUtil, String body) throws Exception {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", cType);
        httppost.setHeader("Content-Type", cType + ";charset=utf-8");
        String src = accountSid + ":" + timestamp;
        String auth = encryptUtil.base64Encoder(src);
        httppost.setHeader("Authorization", auth);
        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
        return httpclient.execute(httppost);
    }


}

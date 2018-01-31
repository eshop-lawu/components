package com.lawu.push.service.impl;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * @author zhangyong
 * @date 2018/1/31.
 */
public class PushCommonClient {
    /**
     * 设置透传模板
     *
     * @param template 透传模板
     * @param title    标题
     * @param contents 内容
     */
    protected static TransmissionTemplate getTemplate(TransmissionTemplate template, String title, String contents) {
        template.setTransmissionContent(contents);
        template.setAPNInfo(getPayLoad(title, contents));
        return template;
    }

    protected static APNPayload getPayLoad(String title, String contents) {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        return payload;
    }

    protected static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String contents) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        //推送显示返回json字符串
        alertMsg.setBody("");
        alertMsg.setActionLocKey("ActionLockey");
        JSONObject jobj = JSON.parseObject(contents);
        //推送展示内容
        alertMsg.setLocKey(jobj.get("content").toString());

        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(title);
        //推送展示标题
        alertMsg.setTitleLocKey(title);
        alertMsg.addTitleLocArg(contents);

        return alertMsg;
    }
}

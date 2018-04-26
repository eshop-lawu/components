package com.lawu.utils;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  个推 推送工程类
 */
public class GtPush {

    private static Logger logger = LoggerFactory.getLogger(GtPush.class);

    public static final String RESULT ="result";

    //毫秒 24小时  24 * 3600 * 1000
    public static final long OFFLINE_EXPIRE_TIME  = 86400000L;

    /**
     * 单推
     *
     * @param contents
     * @param CID
     * @return
     */
    public String sendMessageToCid(String contents, String CID, String title,String host,String appkey,String masterSecret,String appId) {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        TransmissionTemplate template = getTemplateMerchant(title, contents,appId,appkey);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            logger.info("gtPush--服务器响应异常，{}",e);
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            String result = (String) ret.getResponse().get(RESULT);
            logger.info("gtPush result:result({})", result);
            return result;
        } else {
            logger.info("gtPush 推送失败:{}","gtPush--服务器响应异常");
            return "false";
        }
    }

    /**
     * 单推用户端
     *
     * @param contents
     * @param cid
     * @return
     */
    public String sendMessageToCidCustoms(String contents, String cid, String title,String host,String appkey,String masterSecret,String appId) {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        TransmissionTemplate template = getTemplateUser(title, contents,appId,appkey);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            logger.info("RequestException {}",e);
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            String result = (String) ret.getResponse().get(RESULT);
            logger.info("gtpush result:result({})", result);
            return result;
        } else {
            logger.error("gtpush--服务器响应异常");
            return "false";
        }
    }

    /**
     * 推送给所有的商家
     *
     * @param title
     * @param contents
     * @return
     */
    public String pushToAllCompany(String title, String contents,String host,String appkey,String masterSecret,String appId) {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        TransmissionTemplate template = getTemplateMerchant(title, contents,appId,appkey);
        AppMessage message = new AppMessage();
        message.setData(template);

        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        // 推送给App的目标用户需要满足的条件
        List<String> appIdList = new ArrayList<>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);

        IPushResult ret = push.pushMessageToApp(message);
        String result = (String) ret.getResponse().get(RESULT);
        logger.info("gtpush-all-merchant result:result({})", result);
        return result;
    }

    /**
     * 推送给所有用户
     *
     * @param title
     * @param contents
     * @return
     */
    public String pushToAllUser(String title, String contents,String host,String appkey,String masterSecret,String appId) {
        IGtPush push = new IGtPush(host, appkey, masterSecret);
        TransmissionTemplate template = getTemplateUser(title, contents,appId,appkey);
        AppMessage message = new AppMessage();
        message.setData(template);

        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        // 推送给App的目标用户需要满足的条件
        List<String> appIdList = new ArrayList<>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);

        IPushResult ret = push.pushMessageToApp(message);
        String result = (String) ret.getResponse().get(RESULT);
        logger.info("gtpush-all-user result:result({})", result);
        return result;
    }

    /**
     * 推送商家透传模板
     *
     * @param title
     * @param contents
     * @return
     * @author zhangyong
     */
    public static TransmissionTemplate getTemplateMerchant(String title, String contents,String appId,String appkey) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionContent(contents);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setSound("default");
        payload.setContentAvailable(1);
        // 字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        template.setAPNInfo(payload);
        return template;
    }

    /**
     * 用户端推送透传模板
     *
     * @param title
     * @param contents
     * @return
     */
    public static TransmissionTemplate getTemplateUser(String title, String contents,String appId,String appkey) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setTransmissionContent(contents);
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");

        // 字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        template.setAPNInfo(payload);
        return template;
    }

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String contents) {
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

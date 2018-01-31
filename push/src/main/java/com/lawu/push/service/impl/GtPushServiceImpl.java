package com.lawu.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

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
import com.lawu.push.constants.UserTypeEnum;
import com.lawu.push.service.GtPushService;

/**
 * @author zhangyong
 * @date 2018/1/31.
 */
@Service
public class GtPushServiceImpl implements GtPushService, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(GtPushServiceImpl.class);
    /**
     * 毫秒 24小时  24 * 3600 * 1000
     */
    private static final long OFFLINE_EXPIRE_TIME = 86400000L;

    private static final String RESULT = "result";

    private String merchantAppId;

    private String merchantAppKey;

    private String merchantMasterSecret;

    private String memberAppId;

    private String memberAppKey;

    private String memberMasterSecret;

    private String gtHost;

    private TransmissionTemplate memberTemplate;

    private TransmissionTemplate merchantTemplate;

    private IGtPush memberPush;

    private IGtPush merchantPush;

    @Override
    public void afterPropertiesSet() throws Exception {
        memberTemplate = new TransmissionTemplate();
        memberTemplate.setAppId(memberAppId);
        memberTemplate.setAppkey(memberAppKey);
        memberTemplate.setTransmissionType(2);

        merchantTemplate = new TransmissionTemplate();
        merchantTemplate.setAppId(merchantAppId);
        merchantTemplate.setAppkey(merchantAppKey);
        merchantTemplate.setTransmissionType(2);

        memberPush = new IGtPush(gtHost, memberAppKey, memberMasterSecret);

        merchantPush = new IGtPush(gtHost, merchantAppKey, merchantMasterSecret);

    }

    @Override
    public String pushMessageToSingle(String cid, String title, String contents, String prefix) {
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setClientId(cid);
        IPushResult ret;
        try {
            if (UserTypeEnum.MEMBER.getPrefix().equals(prefix)) {
                //用户
                message.setData(getTemplate(memberTemplate, title, contents));
                target.setAppId(memberAppId);
                ret = memberPush.pushMessageToSingle(message, target);
            } else {
                message.setData(getTemplate(merchantTemplate, title, contents));
                target.setAppId(merchantAppId);
                ret = merchantPush.pushMessageToSingle(message, target);
            }
            if (ret != null) {
                String result = (String) ret.getResponse().get(RESULT);
                logger.info("gtPush result:result({})", result);
                return result;
            } else {
                logger.info("gtPush 推送失败:{}", "gtPush--服务器响应异常");
                return "false";
            }
        } catch (RequestException e) {
            logger.info("gtPush--服务器响应异常，{}", e);
        }
        return "false";
    }

    @Override
    public String pushMessageToAll(String title, String contents, String prefix) {
        AppMessage message = new AppMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPIRE_TIME);
        // 推送给App的目标用户需要满足的条件
        List<String> appIdList = new ArrayList<>();
        String result;
        if (UserTypeEnum.MEMBER.getPrefix().equals(prefix)) {
            //用户
            message.setData(getTemplate(memberTemplate, title, contents));
            appIdList.add(memberAppId);
            message.setAppIdList(appIdList);
            IPushResult ret = memberPush.pushMessageToApp(message);
            result = (String) ret.getResponse().get(RESULT);
            logger.info("push-all-member result({})", result);

        } else {
            message.setData(getTemplate(merchantTemplate, title, contents));
            appIdList.add(merchantAppId);
            message.setAppIdList(appIdList);
            IPushResult ret = merchantPush.pushMessageToApp(message);
            result = (String) ret.getResponse().get(RESULT);
            logger.info("push-all-merchant result({})", result);
        }
        return result;
    }


    /**
     * 设置透传模板
     *
     * @param template 透传模板
     * @param title    标题
     * @param contents 内容
     */
    private static TransmissionTemplate getTemplate(TransmissionTemplate template, String title, String contents) {
        template.setTransmissionContent(contents);
        template.setAPNInfo(getPayLoad(title, contents));
        return template;
    }

    private static APNPayload getPayLoad(String title, String contents) {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setAlertMsg(getDictionaryAlertMsg(title, contents));
        return payload;
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

    public void setMerchantAppId(String merchantAppId) {
        this.merchantAppId = merchantAppId;
    }

    public void setMerchantAppKey(String merchantAppKey) {
        this.merchantAppKey = merchantAppKey;
    }

    public void setMerchantMasterSecret(String merchantMasterSecret) {
        this.merchantMasterSecret = merchantMasterSecret;
    }

    public void setMemberAppId(String memberAppId) {
        this.memberAppId = memberAppId;
    }

    public void setMemberAppKey(String memberAppKey) {
        this.memberAppKey = memberAppKey;
    }

    public void setMemberMasterSecret(String memberMasterSecret) {
        this.memberMasterSecret = memberMasterSecret;
    }

    public void setGtHost(String gtHost) {
        this.gtHost = gtHost;
    }

}

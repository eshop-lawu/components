package com.lawu.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lawu.aliyun.AliYunSmsUtil;
import com.lawu.constants.MessageChannelEnum;
import com.lawu.constants.SmsParams;
import com.lawu.ucpaas.client.SmsClient;

/**
 * @author zhangyong
 * @date 2017/12/12.
 */
public class SmsEntrance {
    private static Logger logger = LoggerFactory.getLogger(SmsEntrance.class);

    public static String sendMessage(SmsParams param) {

        if (MessageChannelEnum.CHANNEL_ALIYUN.equals(param.getChannel())) {
            //阿里大鱼
            logger.info("阿里短信发送--------");
            return AliYunSmsUtil.sendMessage(param);
        } else if (MessageChannelEnum.CHANNEL_UCPAAS.equals(param.getChannel())) {
            logger.info("云之讯短信发送--------");
            return new SmsClient().templateSMS(param);
        }
        return null;
    }

}

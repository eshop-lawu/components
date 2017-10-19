package com.lawu.service.monitor.handle.notice;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.MarkdownMessage;
import com.lawu.service.monitor.config.CheckConfig;
import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.DingtalkNoticeConfig;
import com.lawu.service.monitor.result.CheckResult;


/**
 * @author Leach
 * @date 2017/10/17
 */
@Service("dingtalkNoticeHandle")
public class DingtalkNoticeHandle implements NoticeHandle<DingtalkNoticeConfig> {

    private Logger logger = LoggerFactory.getLogger(DingtalkNoticeHandle.class);

    @Autowired
    private CheckConfig checkConfig;

    private DingtalkChatbotClient client = new DingtalkChatbotClient();

    @Override
    public DingtalkNoticeConfig getNoticeConfig() {
        return checkConfig.getNotices().getDingtalk();
    }

    @Override
    public void send(CheckResult checkResult, HttpRequestConfig httpRequestConfig) {
        logger.info("Send notice to dingtalk {}", httpRequestConfig.getUrl());

        DingtalkNoticeConfig noticeConfig = getNoticeConfig();

        String title = "API监控报警-" + checkResult.getNoticeType(httpRequestConfig).getLabel();

        MarkdownMessage message = new MarkdownMessage();
        message.setTitle(title);
        message.add(MarkdownMessage.getHeaderText(2, title));
        message.add("\n\n");
        message.add(MarkdownMessage.getReferenceText(MarkdownMessage.getBoldText("接口地址：")));
        message.add(httpRequestConfig.getUrl());
        message.add("\n\n");
        message.add(MarkdownMessage.getReferenceText(MarkdownMessage.getBoldText("响应状态码：")));
        message.add(String.valueOf(checkResult.getResponseCode()));
        message.add("，期望" + httpRequestConfig.getResponseCode());
        message.add("\n\n");
        message.add(MarkdownMessage.getReferenceText(MarkdownMessage.getBoldText("响应时间：")));
        message.add(checkResult.getResponseTime() + "ms");
        message.add("，期望不大于" + httpRequestConfig.getResponseTime() + "ms");
        message.add("\n\n");
        message.add(MarkdownMessage.getReferenceText(MarkdownMessage.getBoldText("返回信息：")));
        message.add(checkResult.getResponseMsg());
        message.add("\n\n");
        if (!noticeConfig.getAtMobiles().isEmpty()) {

            for (String atMobile : noticeConfig.getAtMobiles()) {
                message.add("@" + atMobile);
            }
            message.add("\n\n");
        }

        message.setAtMobiles(noticeConfig.getAtMobiles());

        try {
            SendResult result = this.client.send(noticeConfig.getWebhookUrl(), message);
            logger.info("Dingtalk result: {}", result);
        } catch (IOException e) {
            logger.error("Send dingtalk notice fail", e);
        }

    }
}

package com.lawu.service.monitor.config.notice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leach
 * @date 2017/10/17
 */
public class DingtalkNoticeConfig extends NoticeConfig {
    private String webhookUrl;
    private List<String> atMobiles = new ArrayList<>();

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }
}

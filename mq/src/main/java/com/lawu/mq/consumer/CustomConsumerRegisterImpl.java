package com.lawu.mq.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/4/11
 */
@Component
public class CustomConsumerRegisterImpl implements CustomConsumerRegister {
    private Map<String, CustomConsumer> customConsumerMap = new HashMap<>();

    private Map<String, List<String>> topics = new HashMap<>();

    /**
     * 注册消费者
     * @param customConsumer
     */
    @Override
    public void registerConsumers(CustomConsumer customConsumer) {
        this.customConsumerMap.put(customConsumer.getTopic() + "-" + customConsumer.getTag(), customConsumer);

        List<String> tags = this.topics.get(customConsumer.getTopic());
        if (tags == null) {
            tags = new ArrayList<>();
            this.topics.put(customConsumer.getTopic(), tags);
        }

        tags.add(customConsumer.getTag());
    }

    /**
     * 获取消费者
     * @param topic
     * @param tag
     * @return
     */
    @Override
    public CustomConsumer getConsumer(String topic, String tag) {
        return this.customConsumerMap.get(topic + "-" + tag);
    }

    /**
     * 获取注册的消费topic信息
     * @return
     */
    @Override
    public Map<String, List<String>> getTopics() {
        return topics;
    }
}

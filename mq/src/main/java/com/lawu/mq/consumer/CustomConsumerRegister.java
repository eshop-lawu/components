package com.lawu.mq.consumer;

import java.util.List;
import java.util.Map;

/**ß
 * @author Leach
 * @date 2017/4/10
 */
public interface CustomConsumerRegister {

    /**
     * 注册消费者
     * @param customConsumer
     */
    void registerConsumers(CustomConsumer customConsumer);

    /**
     * 获取消费者
     * @param topic
     * @param tag
     * @return
     */
    CustomConsumer getConsumer(String topic, String tag);

    /**
     * 获取注册的消费topic信息
     * @return
     */
    Map<String, List<String>> getTopics();
}

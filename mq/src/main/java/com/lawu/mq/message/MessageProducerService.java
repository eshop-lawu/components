package com.lawu.mq.message;

/**
 * @author Leach
 * @date 2017/3/29
 */
public interface MessageProducerService {

    /**
     * 消息发送
     *
     * @param topic 主题
     * @param tags 标签
     * @param message 消息内容
     */
    void sendMessage(String topic, String tags, Object message);

}

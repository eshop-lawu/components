package com.lawu.mq.message.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lawu.mq.message.MessageProducerService;
import com.lawu.mq.utils.ByteUtil;

/**
 * @author Leach
 * @date 2017/4/9
 */
public class RocketMQMessageProducerServiceImpl implements MessageProducerService {

    private Logger logger = LoggerFactory.getLogger(RocketMQMessageProducerServiceImpl.class);

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public void sendMessage(String topic, String tags, Object message) {

        byte[] body;
        try {
            body = ByteUtil.objectToByte(message);
        } catch (IOException e) {

            logger.error("Message body transform fail ", e);
            return;
        }
        try {
            Message msg = new Message(topic, tags, body);
            SendResult send = defaultMQProducer.send(msg);
            logger.info("Message: {},{}", send.getMsgId(), send.getSendStatus().name());
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {

            logger.error("Message send fail ", e);
        }
    }

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }
}

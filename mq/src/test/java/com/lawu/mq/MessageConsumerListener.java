package com.lawu.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lawu.mq.message.impl.AbstractMessageConsumerListener;

/**
 * @author Leach
 * @date 2017/4/11
 */
@Service
public class MessageConsumerListener extends AbstractMessageConsumerListener {
    
    private static Logger logger = LoggerFactory.getLogger(MessageConsumerListener.class);

    @Override
    public void consumeMessage(String topic, String tags, Object message) {
        logger.info("{}, {}, {}", topic, tags, message);
        throw new RuntimeException();
    }
}

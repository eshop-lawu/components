package com.lawu.mq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.lawu.mq.MQAutoConfiguration.RocketMQAutoConfiguration;
import com.lawu.mq.message.MQConsumerFactory;
import com.lawu.mq.message.MessageProducerService;
import com.lawu.mq.message.impl.AbstractMessageConsumerListener;
import com.lawu.mq.message.impl.RocketMQMessageProducerServiceImpl;

/**
 * RocketMQ自动配置类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月22日
 * @updateDate 2017年12月22日
 */
@Configuration
@Import({RocketMQAutoConfiguration.class})
public class MQAutoConfiguration {
    
    // Mock
    @Bean
    @ConditionalOnMissingBean
    public MessageProducerService messageProducerService() {
        return new MessageProducerService() {
            @Override
            public void sendMessage(String topic, String tags, Object message) {
            }
        };
    }
    
    @ConditionalOnClass(DefaultMQProducer.class)
    @ConditionalOnProperty(value = "lawu.mq.rocketmq.namesrvAddr")
    @Configuration
    public static class RocketMQAutoConfiguration {
        
        @Bean(initMethod = "start", destroyMethod = "shutdown")
        @ConfigurationProperties(prefix = "lawu.mq.rocketmq")
        public DefaultMQProducer defaultMQProducer() {
            DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
            defaultMQProducer.setInstanceName("product");
            return defaultMQProducer;
        }
        
        @Bean
        public MessageProducerService messageProducerService() {
            return new RocketMQMessageProducerServiceImpl();
        }
        
        @Bean
        @ConfigurationProperties(prefix = "lawu.mq.rocketmq")
        public DefaultMQPushConsumer defaultMQPushConsumer() {
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
            defaultMQPushConsumer.setInstanceName("consumer");
            //设置为广播方式接受
            defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            /*
             * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
             * 如果非第一次启动，那么按照上次消费的位置继续消费
             */
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            return defaultMQPushConsumer;
        }
        
        @Bean
        @ConditionalOnMissingBean
        public MessageListenerConcurrently messageConsumerListener() {
            return new AbstractMessageConsumerListener() {
                @Override
                public void consumeMessage(String topic, String tags, Object message) {
                }
            };
        }
        
        @ConfigurationProperties(prefix = "lawu.mq.rocketmq")
        @Bean(initMethod = "createDeviceDatagramConsumer", destroyMethod ="shutdown")
        public MQConsumerFactory defaultMQConsumerFactory() {
            MQConsumerFactory defaultMQConsumerFactory = new MQConsumerFactory();
            return defaultMQConsumerFactory;
        }
    }
}

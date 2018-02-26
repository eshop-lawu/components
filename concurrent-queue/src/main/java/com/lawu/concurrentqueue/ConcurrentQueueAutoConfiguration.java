package com.lawu.concurrentqueue;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.lawu.concurrentqueue.bizctrl.BusinessDecisionAspect;
import com.lawu.concurrentqueue.requestctrl.ConcurrentTaskExecutor;


/**
 * 并发队列控制组件自动配置
 * 
 * @author jiangxinjun
 * @createDate 2018年2月24日
 * @updateDate 2018年2月24日
 */
@ConditionalOnProperty(name = { "lawu.concurrent-queue.enabled" }, havingValue = "true", matchIfMissing = false)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@Import({ BusinessDecisionAspect.class })
public class ConcurrentQueueAutoConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "lawu.concurrent-queue.task-executor")
    public ConcurrentTaskExecutor concurrentTaskExecutor() {
        ConcurrentTaskExecutor concurrentTaskExecutor = new ConcurrentTaskExecutor();
        return concurrentTaskExecutor;
    }
}

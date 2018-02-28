package com.lawu.framework.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lawu.framework.core.FrameworkCoreAutoConfiguration.EventAutoConfiguration;
import com.lawu.framework.core.event.AsyncEventApplicationListener;
import com.lawu.framework.core.event.SyncEventApplicationListener;

/**
 * FrameworkCore自动配置类
 * @author jiangxinjun
 * @createDate 2018年2月28日
 * @updateDate 2018年2月28日
 */
@Configuration
@Import({ EventAutoConfiguration.class })
public class FrameworkCoreAutoConfiguration {
    
    /**
     * 事件自动配置类
     * @author jiangxinjun
     * @createDate 2018年2月28日
     * @updateDate 2018年2月28日
     */
    @ConditionalOnProperty(name = {"lawu.framework-core.event.enabled"}, havingValue="true", matchIfMissing = false)
    @Configuration
    @EnableAsync
    @Import(value = {AsyncEventApplicationListener.class, SyncEventApplicationListener.class})
    public static class EventAutoConfiguration {
        
        @ConfigurationProperties(prefix = "lawu.framework-core.executor")
        @Bean(initMethod = "initialize")
        public ThreadPoolTaskExecutor threadPoolTaskExecutor() {  
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(20);
            return executor;  
        }  
        
    }

}

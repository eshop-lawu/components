package com.lawu.idworker.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.lawu.idworker.client.IdWorkerClientAutoConfiguration.IdWorkerServiceAutoConfiguration;
import com.lawu.idworker.client.service.IdWorkerService;
import com.lawu.idworker.client.util.IdWorkerHelper;

/**
 * IdWorkerClient自动配置类
 * 
 * @author jiangxinjun
 * @createDate 2018年4月13日
 * @updateDate 2018年4月13日
 */
@Configuration
@ImportAutoConfiguration({ IdWorkerServiceAutoConfiguration.class })
public class IdWorkerClientAutoConfiguration implements InitializingBean {
    
    /**
     * 远程访问服务
     */
    @Autowired(required = false)
    private IdWorkerService idWorkerService;

    @Override
    public void afterPropertiesSet() throws Exception {
        IdWorkerHelper.setIdWorkerService(idWorkerService);
    }
    
    @ConditionalOnProperty(value = "com.lawu.id-worker-client.enabled", havingValue = "true", matchIfMissing = false)
    @EnableFeignClients(basePackageClasses = { IdWorkerService.class })
    public static class IdWorkerServiceAutoConfiguration  {
    }

}

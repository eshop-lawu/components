package com.lawu.idworker.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.lawu.idworker.client.service.IdWorkerService;
import com.lawu.idworker.client.util.IdWorkerHelper;

/**
 * IdWorkerClient自动配置类
 * 
 * @author jiangxinjun
 * @createDate 2018年4月13日
 * @updateDate 2018年4月13日
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {IdWorkerService.class})
@Configuration
public class IdWorkerClientAutoConfiguration implements InitializingBean {
    
    /**
     * 远程访问服务
     */
    @Autowired
    private IdWorkerService idWorkerService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        IdWorkerHelper.setIdWorkerService(idWorkerService);
    }
    
}

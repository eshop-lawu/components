package com.lawu.id.worker.generate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lawu.id.worker.generate.config.IdWorkerConfig;
import com.lawu.id.worker.generate.service.IdWorkerService;
import com.lawu.id.worker.generate.service.impl.IdWorkerServiceImpl;

@Configuration
@Import({IdWorkerConfig.class})
public class IdWorkerAutoConfig {
    
    @Bean
    public IdWorker idWorker(IdWorkerConfig idWorkerConfig){
        return new IdWorker(idWorkerConfig.getWorkerId(), idWorkerConfig.getDataCenterId());
    }
    
    @Bean 
    public IdWorkerService idWorkerService() {
        return new IdWorkerServiceImpl();
    }
}
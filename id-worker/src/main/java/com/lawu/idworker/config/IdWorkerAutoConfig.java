package com.lawu.idworker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lawu.idworker.util.IdWorker;

@Configuration
public class IdWorkerAutoConfig {
    
    @Autowired
    private IdWorkerConfig idWorkerConfig;
    
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(idWorkerConfig.getWorkerId(), idWorkerConfig.getDataCenterId(), idWorkerConfig.getMaximum());
    }
}
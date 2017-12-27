package com.lawu.compensating.transaction;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.lawu.compensating.transaction.properties.TransactionJobProperties;
import com.lawu.compensating.transaction.service.CacheService;
import com.lawu.compensating.transaction.service.FollowTransactionRecordService;
import com.lawu.compensating.transaction.service.impl.CacheServiceImpl;
import com.lawu.compensating.transaction.service.impl.FollowTransactionRecordServiceImpl;
import com.lawu.compensating.transaction.service.impl.TransactionStatusServiceImpl;

/**
 * RocketMQ自动配置类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月22日
 * @updateDate 2017年12月22日
 */
@Profile("compensating-transaction")
@Configuration
@EnableConfigurationProperties({ TransactionJobProperties.class })
@Import({TransactionInitializing.class})
public class CompensatingTransactionAutoConfiguration {
    
    @Bean
    public CacheService cacheService() {
        return new CacheServiceImpl();
    }
    
    @Bean
    public FollowTransactionRecordService followTransactionRecordService() {
        return new FollowTransactionRecordServiceImpl();
    }
    
    @Bean
    public TransactionStatusService transactionStatusService() {
        return new TransactionStatusServiceImpl();
    }
}

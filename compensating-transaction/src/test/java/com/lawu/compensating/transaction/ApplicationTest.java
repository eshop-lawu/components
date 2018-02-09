package com.lawu.compensating.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@EnableTransactionManagement
@SpringBootApplication
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
    
    @ConditionalOnProperty(value = "zookeeper.servers")
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(@Value("${zookeeper.servers}") String serverLists) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverLists, "compensating-transaction-job"));
    }
}

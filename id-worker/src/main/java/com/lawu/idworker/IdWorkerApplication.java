package com.lawu.idworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ID派发器服务启动类
 * @author Leach
 * @date 2017/10/24
 */
@EnableDiscoveryClient
@ComponentScan({"com.lawu"})
@SpringBootApplication
public class IdWorkerApplication {

    private static Logger logger = LoggerFactory.getLogger(IdWorkerApplication.class);

    public static void main(String[] args) {
        logger.info("id-worker is starting");
        SpringApplication.run(IdWorkerApplication.class, args);
        logger.info("id-worker is started");
    }
}

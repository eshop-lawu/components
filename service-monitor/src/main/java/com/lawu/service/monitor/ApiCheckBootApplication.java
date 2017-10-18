package com.lawu.service.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Leach
 * @date 2017/10/16
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.lawu.service.monitor"})
public class ApiCheckBootApplication {

    private static Logger logger = LoggerFactory.getLogger(ApiCheckBootApplication.class);

    public static void main(String[] args) {
        logger.info("ServiceMonitor is starting");
        SpringApplication.run(ApiCheckBootApplication.class, args);
        logger.info("ServiceMonitor is started");
    }
}

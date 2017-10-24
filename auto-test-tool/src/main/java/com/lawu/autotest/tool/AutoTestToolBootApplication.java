package com.lawu.autotest.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Leach
 * @date 2017/10/23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lawu.autotest.tool"})
public class AutoTestToolBootApplication {

    private static Logger logger = LoggerFactory.getLogger(AutoTestToolBootApplication.class);

    public static void main(String[] args) {
        logger.info("AutoTestTool is starting");
        SpringApplication.run(AutoTestToolBootApplication.class, args);
        logger.info("AutoTestTool is started");
    }
}

package com.lawu.weixinapi;

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
@ComponentScan(basePackages = {"com.lawu.weixinapi"})
public class WeixinApiBootApplication {

    private static Logger logger = LoggerFactory.getLogger(WeixinApiBootApplication.class);

    public static void main(String[] args) {
        logger.info("WeixinApi is starting");
        SpringApplication.run(WeixinApiBootApplication.class, args);
        logger.info("WeixinApi is started");
    }
}

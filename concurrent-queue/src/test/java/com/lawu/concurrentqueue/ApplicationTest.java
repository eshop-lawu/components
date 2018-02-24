package com.lawu.concurrentqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@ImportResource(locations={"classpath:spring-test.xml"})
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
    
}

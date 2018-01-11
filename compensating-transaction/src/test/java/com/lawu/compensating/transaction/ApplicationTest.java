package com.lawu.compensating.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource(locations={"classpath:spring.xml"})
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}

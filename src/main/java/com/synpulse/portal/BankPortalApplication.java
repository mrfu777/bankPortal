package com.synpulse.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.synpulse.portal.dao"})
public class BankPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankPortalApplication.class, args);
    }
}
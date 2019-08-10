package com.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 样例
 * @author: sca
 * @create: 2019-07-18 18:22
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.security.web.mapper")
public class BrowserDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrowserDemoApplication.class , args);
    }
}

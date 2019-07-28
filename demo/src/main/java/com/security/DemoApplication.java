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
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class , args);
    }
}

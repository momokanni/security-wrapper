package com.security.service.impl;

import com.security.service.DemoUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: demo user 逻辑层
 * @author: sca
 * @create: 2019-07-19 21:27
 **/
@Slf4j
@Service
public class DemoUserServiceImpl implements DemoUserService {


    @Override
    public String doSomething(String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("你好,").append(userName).append(". ").append("这里是自定义注解demo版");
        log.info(sb.toString());
        return "access";
    }
}

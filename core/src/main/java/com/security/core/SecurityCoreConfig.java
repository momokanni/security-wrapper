package com.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.security.core.properties.SecurityProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 作用：使配置读取器生效
 * core: 1、加载系统配置类，并使配置生效
 * 		 2、app、browser公共方法
 * @author sca
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 密码的加密解密
     * 1、用户登录，UserNamePasswordAuthenticationFilter获取到用户输入的密码，
     * 2、通过PasswordEncoder的matches方法同数据库加密保存的密码进行比对
     * 3、匹配成功matches()方法返回true
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

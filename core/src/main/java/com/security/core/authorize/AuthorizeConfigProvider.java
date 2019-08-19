package com.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @description: 动态授权接口
 * @author: sca
 * @create: 2019-08-18 23:08
 **/
public interface AuthorizeConfigProvider {

    /**
     * 授权配置提供者接口
     * @param config
     * @return
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}

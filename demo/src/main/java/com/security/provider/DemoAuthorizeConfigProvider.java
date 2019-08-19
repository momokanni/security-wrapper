package com.security.provider;

import com.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: 授权配置提供者
 * @author: sca
 * @create: 2019-08-18 23:34
 **/
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        return false;
    }
}

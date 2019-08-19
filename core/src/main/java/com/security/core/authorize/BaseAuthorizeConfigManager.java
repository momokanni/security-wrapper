package com.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @description: 动态授权提供者的管理接口的实现基类
 * @author: sca
 * @create: 2019-08-18 23:23
 **/
@Component
public class BaseAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private Set<AuthorizeConfigProvider> authorizeConfigProviderSet;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        for (AuthorizeConfigProvider provider : authorizeConfigProviderSet){
            provider.config(config);
        }
        config.anyRequest().authenticated();
    }
}

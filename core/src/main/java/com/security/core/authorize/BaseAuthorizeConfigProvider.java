package com.security.core.authorize;

import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: 动态授权接口实现类
 * @author: sca
 * @create: 2019-08-18 23:15
 **/
@Component
public class BaseAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getBrowser().getSignUp(),
                securityProperties.getBrowser().getLoginOutUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                "/user/register")
            .permitAll();
    }
}

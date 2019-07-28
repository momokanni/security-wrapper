package com.security.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import com.security.browser.logout.LoginOutSuccessHandler;
import com.security.browser.session.ExpiredSessionStrategy;
import com.security.browser.session.InvalidSessionStrategy;
import com.security.core.properties.SecurityProperties;

/**
 * @Description  浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全模块默认的配置。
 * @Author sca
 * @Date 2019-07-24 11:16
 **/
@Configuration
public class BrowserSecurityBeanConfig {
	
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy() {
		return new InvalidSessionStrategy(securityProperties);
	}
	
	/**
	 * 并发登录导致前一个session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		
		return new ExpiredSessionStrategy(securityProperties);
	}
	
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler() {

		return new LoginOutSuccessHandler(securityProperties.getBrowser().getLoginOutUrl());
	}
}

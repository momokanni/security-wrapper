package com.security.core.authencation.sms;

import com.security.core.service.SuperUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Description 短信验证码登录配置类
 * @Author sca
 * @Date 2019-07-24 11:35
 **/
@Component(value = "smsCodeAuthenticationConfig")
public class SmsCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler authSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authFailureHandler;
	
	@Autowired
	private SuperUserDetailsService superUserDetailsService;
	
	@Override
	public void configure(HttpSecurity http){
		
		/**
		 * Filter配置
		 */
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		//1、给该Filter指定AuthenticationManager
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		//2、指定请求成功handler 这是Filter固定配置
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);
		//3、指定请求失败handler 这是Filter固定配置
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authFailureHandler);
		
		/**
		 * Provider 配置
		 */
		SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
		provider.setSuperUserDetailsService(superUserDetailsService);
		
		// 添加自定义provider到AuthenticationManager的private List<AuthenticationProvider> providers集合中
		http.authenticationProvider(provider)
			// 添加【短信认证过滤器】到UPAF后
			.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}

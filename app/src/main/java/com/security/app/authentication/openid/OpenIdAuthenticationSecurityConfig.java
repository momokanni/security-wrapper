package com.security.app.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author sca
 * 将openIdFilter和openIdProvider加入FilterChain
 * 简单模式
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler ownAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler ownAuthenctiationFailureHandler;
	
	@Autowired
	private SocialUserDetailsService userDetailsService;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		OpenidAuthenticationFilter filter = new OpenidAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(ownAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(ownAuthenctiationFailureHandler);
		
		OpenIdAuthenticationProvider provider = new OpenIdAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setUsersConnectionRepository(usersConnectionRepository);
		
		http.authenticationProvider(provider)
		.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
	}
}

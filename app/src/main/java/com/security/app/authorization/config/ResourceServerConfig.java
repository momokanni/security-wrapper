package com.security.app.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;
import com.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.security.core.authencation.sms.SmsCodeAuthenticationConfig;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.validator.config.ValidateCodeConfig;

@Configuration
/**
  * 开启资源服务器
  */
@EnableResourceServer
/**
 * @Description
 * @author sca
 * @Date 2019-08-03 17:54
 **/
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityPro;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private SmsCodeAuthenticationConfig smsAuthConfig;
	
	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;
	
	@Autowired
	private ValidateCodeConfig validateCodeSecurityConfig;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.formLogin()
		.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
		.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailureHandler);
		
		http
		.apply(validateCodeSecurityConfig)
			.and()
		.apply(smsAuthConfig)
			.and()
		.apply(socialSecurityConfig)
			.and()
		.apply(openIdSecurityConfig)
			.and()
		.authorizeRequests()
			.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
					securityPro.getBrowser().getLoginPage(),
					securityPro.getBrowser().getSignUp(),
					securityPro.getBrowser().getLoginOutUrl(),
					securityPro.getBrowser().getSession().getSessionInvalidUrl(),
					"/user/register")
				.permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.csrf().disable();
	}
}

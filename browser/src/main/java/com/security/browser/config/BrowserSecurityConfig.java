package com.security.browser.config;

import javax.sql.DataSource;

import com.security.core.authorize.AuthorizeConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;
import com.security.core.authencation.AbstractChannelSecurityConfig;
import com.security.core.authencation.sms.SmsCodeAuthenticationConfig;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.validator.config.ValidateCodeConfig;

/**
 * @Description PC web端安全配置
 * @Author sca
 * @Date 2019-07-24 11:15
 **/
@Slf4j
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{
	
	@Autowired
	private SecurityProperties securityPro;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService uds;
	
	@Autowired
	private SmsCodeAuthenticationConfig smsCodeAuthenticationConfig;
	
	@Autowired
	private ValidateCodeConfig validateCodeConfig;
	
	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionStrategy;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	/**
	 * 登录记住我功能 --> 
	 * 		1、创建TokenRepository去读写DB
	 * 		2、给其指定数据源
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		/**
		 * 第一次启动时创建数据库表
		 * tokenRepository.setCreateTableOnStartup(true);
		 */
		return tokenRepository;
	}
	
	/**
	 * 流程：多个Filter组合在一起
	 * UserNamePasswordAuthenticationFilter(username,password)（只会处理 /login ,post请求）, BasicAuthenticationFilter
	 *  ↓
	 *  ↓                  ExceptionTranslationFilter
	 *  ↓                   ↑
	 * FilterSecurityInterceptor
	 *  ↓ 
	 * Rest API
	 * 
	 * 详解：当请求路径没有携带任何登录参数的话，该请求不经过UserNamePasswordAuthenticationFilter验证，直接转到最后一层
	 * FilterSecurityInterceptor 进行验证：
	 * 		doFilter() -> invoke(FilterInvocation fi) 
	 * 		-> InterceptorStatusToken token = super.beforeInvocation(fi);(最终判断逻辑)
	 * 当验证不通过抛出异常，抛给ExceptionTranslationFilter，由它进行处理，
	 * 最终的处理是重定向到一个默认的登录页面上
	 * ↓
	 * 登录页面输入完指定参数后（该页面表单login提交post请求）
	 * ↓
	 * UserNamePasswordAuthenticationFilter（只处理login请求）
	 * ↓ 
	 * ↓ login success
	 * ↓
	 * FilterSecurityInterceptor (继续处理认证失败之前的请求)
	 * ↓
	 * Rest API
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		applyPasswordAuthenticationConfig(http);
		 
		//表单登录，指明了：身份认证的方式 （所有请求）
		http
			.apply(validateCodeConfig)
			.and()
			.apply(smsCodeAuthenticationConfig)
			.and()
			.apply(socialSecurityConfig)
			.and()
			//【记住我】配置，如果想在'记住我'登录时记录日志，
			//可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
			.rememberMe()
				// 指定数据源
				.tokenRepository(persistentTokenRepository())
				// 超时设置
				.tokenValiditySeconds(securityPro.getBrowser().getRememberMeSeconds())
				// 指定UserDetailsService
				.userDetailsService(uds)
				//httpBasic() 表单登录弹窗
			.and()
			.sessionManagement() 
				.invalidSessionStrategy(invalidSessionStrategy)
				// 并发控制 session最大个数
				.maximumSessions(securityPro.getBrowser().getSession().getMaximumSessions())
				// 并发控制 当第一次登录未过期，则阻止其他地方第二次登录
				.maxSessionsPreventsLogin(securityPro.getBrowser().getSession().isMaxSessionPreventsLogin())
				// 超时
				.expiredSessionStrategy(sessionStrategy) 
			.and()
			.and()
			.logout()
				// 默认退出路径：/logout
				.logoutUrl("/loginOut") 
				.logoutSuccessHandler(logoutSuccessHandler)
				// 删除浏览器cookie
				.deleteCookies("JSEESIONID") 
			.and() 
//			.authorizeRequests() //下面两行都是授权配置
//				.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
//						securityPro.getBrowser().getLoginPage(),
//						securityPro.getBrowser().getSignUp(),
//						securityPro.getBrowser().getLoginOutUrl(),
//						securityPro.getBrowser().getSession().getSessionInvalidUrl(),
//						"/user/register")
//					.permitAll()
//				//.antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")
//				.antMatchers(HttpMethod.GET,"/user/*").access("hasRole('ADMIN') and hasIpAdress('192.168.0.1/24')")
//				.anyRequest() //任何请求
//				.authenticated()
//			.and()
			.csrf().disable();//禁用跨域请求，默认
		authorizeConfigManager.config(http.authorizeRequests());
	}

	@Override
	public void configure(WebSecurity web) {
		String[] avoidUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityPro.getBrowser().getStaticAvoidPath(), ",");
		//解决静态资源被拦截的问题
		log.info("需要规避拦截的静态资源路径有: {}" , securityPro.getBrowser().getStaticAvoidPath());
		web.ignoring().antMatchers(avoidUrl);
	}
}

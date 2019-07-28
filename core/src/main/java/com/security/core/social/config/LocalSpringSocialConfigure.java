package com.security.core.social.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.security.core.social.interfaces.SocialAuthenticationFilterPostProcessor;

/**
 * 
 * @author sca
 * {@link SpringSocialConfigurer} Configurer that adds {@link SocialAuthenticationFilter} to Spring Security's filter chain.
 * 通过继承SpringSocialConfigurer重写postProcess方法来修改SocialAuthenticationFilter的默认配置
 */
public class LocalSpringSocialConfigure extends SpringSocialConfigurer{
	
	//社交认证成功处理器
	@Getter
	@Setter
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;
	
	private String processesUrl;
	
	public LocalSpringSocialConfigure(String processesUrl) {
		this.processesUrl = processesUrl;
	}
	
	
	/**
	 * 重写postProcess方法的原因:
	 * 修改SpringSocialConfigurer的configure对于SocialAuthenticationFilter的默认配置
	 * 1. SocialAuthenticationFilter: filterProcessesUrl【社交登录请求地址前缀】
	 * 								  源码的处理逻辑: 1. 判断请求路径和配置的是否相同
	 * 								  				2. 如果相同，则将filterProcessesUrl作为标识为截取出providerId(服务商标识)
	 * 								  				3. 通过providerId获取OAuth2认证处理service: OAuth2AuthenticationService
	 * 								  				4. OAuth2AuthenticationService.getAuthToken()
	 * 								  					4.1	OAuthOperations(实现类: OAuth2Template).exchangeForAccess
	 * 								  					4.2 OAuth2ConnectionFactory.createConnection() 获取对应的ConnectionFactory
	 * 								  					4.3 OAuth2ServiceProvider
	 * 								  					4.4
	 * 								  					4.5 根据请求结果组装成connection
	 * 								  					4.6 再将connection封装成对应的token(SocialAuthenticationToken)
	 * 								  				5. addConnection(..)
	 * 								  					5.1 addConnection(..) insert DB
	 * 2. 指定社交认证后处理器
	 *
	 */
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(processesUrl);
		/**
		 * 需要对socialAuthenticationFilterPostProcessor进行判断
		 * 原因 ：1、browser使用默认的后处理器即可，所以browser社交登录认证时，此处理器对象为null == springBoot默认后处理器
		 * 	 	 2、app需要指定，因为app需要返回生成的认证token，所以app社交登录认证时，此对象!=null,
		 * 	 	    会调用SocialAuthenticationFilterPostProcessor的实现
		 */
		if(socialAuthenticationFilterPostProcessor != null) {
			socialAuthenticationFilterPostProcessor.process(filter); 
		}
		return (T) filter;
	}
}
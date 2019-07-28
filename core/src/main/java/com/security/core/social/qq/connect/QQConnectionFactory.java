package com.security.core.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import com.security.core.social.qq.api.QQ;

/**
 * 1、SocialAuthenticationService 会自动加载程序中的 ConnectionFactory(由ServiceProvider和Adapter构成)
 * --> 2、服务提供商的信息  -（封装）-> 3、Connection -（封装）-> 4、SocialAuthenticationToken 
 * --> 5、AuthenticationManager (ProviderManager) 
 * --> 6、找到合适的AuthenticationProvider处理 :SocialAuthenticationToken
 *     而Provider会根据SocialAuthenticationToken里封装的Connection的服务商信息，
 *     确定具体使用哪个Repository,到DB查询出UserId
 * --> UserId作为参数，调用SocialUserDetailsService去业务系统（Spring-demo MyUserDetailsService）
 *     将用户信息查询出来 SocialUserDetails
 *     并放入SocialAuthenticationToken,同时标记成已认证状态 
 * --> 存入 SecurityContext中 最终放入到session中
 * @author sca
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	/**
	 * @param providerId 服务提供商的唯一标识
	 */
	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}

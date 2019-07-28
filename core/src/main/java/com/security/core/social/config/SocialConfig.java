package com.security.core.social.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import com.security.core.properties.SecurityProperties;
import com.security.core.social.interfaces.SocialAuthenticationFilterPostProcessor;

/**
 * @author sca
 * @class SocialConfigurerAdapter 社交配置适配器 【本地化配置】用来指定 连接工厂、数据源、社交认证后处理器
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;
	
	/**
	 * 默认注册
	 */
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;
	
	/**
	 * 获取数据库链接
	 * @param connectionFactoryLocator 根据条件查找系统中应该使用的具体是哪个social的connectionFactory
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

		// 修改UserConnection表名只能在默认表名前 + 前缀：security_userConnection
		// JdbcUsersConnectionRepository  repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		// repository.setTablePrefix("huzhu_"); // 设定表名前缀
		// return repository;
		
		//默认表名
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		if(connectionSignUp != null) {
			// 三方登录默认注册
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}
	
	/**
	 * social配置类
	 * @return
	 * core需要指定 "社交认证过滤器后处理器"
	 * 为什么需要指定： 因为browser和app认证成功后的处理机制不同，
	 * 			  browser：认证成功后跳转
	 * 			  app： 认证成功后需要获取返回的token（令牌）
	 * SpringSocialConfigurer官方解释: 
	 * 			  Configurer that adds {@link SocialAuthenticationFilter} to Spring Security's filter chain.
	 */
	@Bean
	public SpringSocialConfigurer socialSecurityConfig() {
		String filterProcessUrl = securityProperties.getSocial().getFilterProcessUrl();
		LocalSpringSocialConfigure lssc = new LocalSpringSocialConfigure(filterProcessUrl);
		/**
		 * 修改默认注册页
		 * SocialAuthticationFilter捕获SocialAuthenticationProvider在获取不到系统userId时抛出的BadCredentialsException异常，
		 * 这时会跳转到默认或者自定义signUpUrl上
		 * 源码: 
		 * SocialAuthenticationProvider authenticate();
		 * SocialAuthticationFilter attemptAuthentication();
		 * 		
		 */
		lssc.signupUrl(securityProperties.getBrowser().getSignUp());
		lssc.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
		return lssc;
	}
	
	/**
	 * 1. 解决了在三方登录为注册的情况下，如何拿到springSocial信息
	 * 2. 解决了注册完成后，如何再把业务系统的userId传给springSocial
	 * 构造方法注入
	 * 场景： 1. 当非注册用户点击QQ 登录，认证不通过，跳转至默认或者自定义的signUpUrl，在该页显示用户登录QQ的基本信息体验更友好。
	 *       2. 三方登录成功后，点击注册/绑定，提交信息到controller，然后怎么把social用户信息和平台创建的用户绑定到一块，并存入DB
	 * 该类作用：就是解决以上两个场景问题
	 * 解决方案：通过ProviderSignInUtils将业务处理数据和session中的social数据整合到一块
	 *         而session的social信息是在SocailAuthenticationFilter捕获BadCredentialsException时存入的
	 * @param connectionFactoryLocator 用以定位connectionFactory
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}
}

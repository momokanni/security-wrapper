package com.security.core.social.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.web.servlet.View;
import com.security.core.properties.SecurityProperties;
import com.security.core.properties.WeixinProperties;
import com.security.core.social.view.ConnectResultView;
import com.security.core.social.wechat.connect.WeiXinConnectionFactory;

/**
 * 微信登录配置
 * @author sca
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "com.security.social.weixin", name = "app-id")
public class WeiXinAutoConfiguration extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;


	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties wx = securityProperties.getSocial().getWeixin();
		return new WeiXinConnectionFactory(wx.getProviderId(),
										   wx.getAppId(),
										   wx.getAppSecret());
	}

	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

	
	@Bean({"connect/weiXinConnect","connect/weiXinConnected"})
	@ConditionalOnMissingBean(name = "weiXinConnectedView")
	public View weixinConnectedView() {
		
		return new ConnectResultView();
	}
	
}

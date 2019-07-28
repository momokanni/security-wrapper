package com.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import com.security.core.properties.QQProperties;
import com.security.core.properties.SecurityProperties;
import com.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
//配置文件中存在该配置（com.security.social.qq.app-id）则生效，无配置不生效
@ConditionalOnProperty(prefix = "com.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {
	
	@Autowired
	private SecurityProperties sp;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(createConnectionFactory());
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	protected ConnectionFactory<?> createConnectionFactory() {
		
		QQProperties qqPro = sp.getSocial().getQq();
		
		return new QQConnectionFactory(qqPro.getProviderId(),
									   qqPro.getAppId(),
									   qqPro.getAppSecret());
	}
}
  
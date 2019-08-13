package com.security.app.authorization.config;

import com.security.core.store.CoreRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import com.security.core.properties.SecurityProperties;

/**
 * @author sca
 * 令牌存储
 */
@Configuration
public class TokenStoreConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Bean
	@ConditionalOnProperty(prefix = "com.security.oauth", name = "storeType", havingValue = "redis")
	public TokenStore redisTokenStore() {

		return new CoreRedisTokenStore(redisConnectionFactory);
	}
	
	/**
	 * JWT Token的特点：自包含、密签、可扩展
	 * @author Administrator
	 */
	@Configuration
	/**
	 * 当demo配置文件里配置项为：security.oauth.storeType = jwt,则该内部静态类配置生效
	 * matchIfMissing = true:未配置也正常生效
 	 */
	@ConditionalOnProperty(prefix = "com.security.oauth", name = "storeType", havingValue = "jwt", matchIfMissing = true)
	public static class JwtTokenConfig{
		
		@Autowired
		private SecurityProperties securityProperties;
		
		/**
		 * 存放token
		 * @return
		 */
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}
		
		/**
		 * 对token生成做一些"转换"处理
		 * 例如：密签
		 * @return
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			
			JwtAccessTokenConverter accessTokenConvert = new JwtAccessTokenConverter();
			accessTokenConvert.setSigningKey(securityProperties.getOauth().getJwtSigningKey());
			return accessTokenConvert;
		}
	}
}

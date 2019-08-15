package com.security.app.authorization.config;

import java.util.ArrayList;
import java.util.List;
import com.security.app.jwt.LocalJwtTokenEnhancer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import com.security.core.properties.OAuth2ClientProperties;
import com.security.core.properties.SecurityProperties;


@Component
/**
 * 开启认证服务
 */
@EnableAuthorizationServer
/**
 * @Description 授权服务类
 * @author sca
 * @Date 2019-08-03 18:23
 **/
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	/**
	 * 构造函数注入
	 */
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtTokenConverter;
	
	@Autowired(required = false)
	private LocalJwtTokenEnhancer tokenEnhancer;
	
	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 注入AuthenticationManager
	 * @param authenticationConfiguration
	 * @throws Exception
	 */
	public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {

		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * 配置授权认证访问入口,将自定义的配置类注入到授权流程中去
	 * TokenEndPoint
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

		endpoints.tokenStore(tokenStore)
				 .authenticationManager(authenticationManager)
				 .userDetailsService(userDetailService);
		if(jwtTokenConverter != null && tokenEnhancer != null) {
			/**
			 * endpoints.accessTokenConverter(jwtTokenConverter);
			 * 增强器链
			 * 将默认的token借由jwtTokenConverter转换成jwt，再通过jwtTokenEnhancer添加自定义信息
			 */
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(tokenEnhancer);
			enhancers.add(jwtTokenConverter);
			enhancerChain.setTokenEnhancers(enhancers);
			
			endpoints.tokenEnhancer(enhancerChain)
					 .accessTokenConverter(jwtTokenConverter);
		}
	}
	
	/**
	 * 客户端配置（指定可访问的客户端，及其可访问权限）
	 * 根据配置指定可以给哪些客户端发令牌
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        /**
         * 获取客户端配置
         */
		List<OAuth2ClientProperties> clientCollection = securityProperties.getOauth().getClients();
        if (CollectionUtils.isNotEmpty(clientCollection)) {
            for (OAuth2ClientProperties client : securityProperties.getOauth().getClients()) {
                builder.withClient(client.getClientId())
                        .secret(passwordEncoder.encode(client.getClientSecret()))
                        .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                        .authorizedGrantTypes("refresh_token","password")
                        .scopes("all","read","write");
            }
        }
	}
}

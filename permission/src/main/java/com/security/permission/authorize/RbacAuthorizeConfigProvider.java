package com.security.permission.authorize;

import com.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 11:36
 **/
@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/**
	 *	non-Javadoc)
	 * @see com.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config
			.antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
			.antMatchers(HttpMethod.GET, 
					"/**/*.html",
					"/admin/me",
					"/resource").authenticated()
			.anyRequest()
				.access("@rbacService.hasPermission(request, authentication)");
		return true;
	}

}

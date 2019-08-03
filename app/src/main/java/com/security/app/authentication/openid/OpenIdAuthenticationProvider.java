package com.security.app.authentication.openid;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author sca
 * 简单认证模式：
 * 		通过openid和providerId获取security_app(资源认证服务器)的token
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

	private SocialUserDetailsService userDetailsService;
	
	private UsersConnectionRepository usersConnectionRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
		
		Set<String> openId = new HashSet<>();
		openId.add((String)authentication.getPrincipal());
		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), openId);
		
		if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		String userId = userIds.iterator().next();
		
		UserDetails user = userDetailsService.loadUserByUserId(userId);
		
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
		
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	
	@Override
	public boolean supports(Class<?> authentication) {

		return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
	}


	public SocialUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}


	public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	public UsersConnectionRepository getUsersConnectionRepository() {
		return usersConnectionRepository;
	}


	public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
		this.usersConnectionRepository = usersConnectionRepository;
	}

}

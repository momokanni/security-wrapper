package com.security.core.social;

import org.springframework.social.connect.Connection;

import com.security.core.support.SocialUserInfo;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:24
 **/
public abstract class AbstractSocialController {
	
	protected SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
		
		SocialUserInfo userInfo = new SocialUserInfo();
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}
}

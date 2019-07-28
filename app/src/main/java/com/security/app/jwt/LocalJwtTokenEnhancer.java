package com.security.app.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author sca
 * Token增强器：改造令牌，添加一些自定义的信息
 */
public class LocalJwtTokenEnhancer implements TokenEnhancer {

	/**
	 * 当组装好OAuth2Authentication去生成最终的token时
	 * 调用生成方法：(私有：无法覆盖)private createAccessToken(..){
	 * 		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
	 * 		...
	 * 		return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token,OAuth2Authentication) : token
	 * }
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String,Object> info = new HashMap<>();
		info.put("company", "txhl");
		
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}

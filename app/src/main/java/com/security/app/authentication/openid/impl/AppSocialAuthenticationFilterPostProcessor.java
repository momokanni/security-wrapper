package com.security.app.authentication.openid.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.security.core.social.interfaces.SocialAuthenticationFilterPostProcessor;

/**
 * @author sca
 * 指定app社交登录成功后的处理逻辑
 * 为什么要特殊指定：
 * 			   1、浏览器支持session(JSESSIONID)存储，而app不支持，只能使用token
 * 			   2、security-core中的社交配置类LocalSpringSocialConfigure中的
 * 			   SocialAuthenticationFilter调用的还是springBoot默认的认证成功后处理器，并不会返给app需要的token
 * 			         而是还会像浏览器社交登录一样跳转到其他路径上。
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {
	
	@Autowired
	private AuthenticationSuccessHandler ownAuthenticationSuccessHandler;
	
	@Override
	public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
		socialAuthenticationFilter.setAuthenticationSuccessHandler(ownAuthenticationSuccessHandler);
	}

}

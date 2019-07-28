/**
 * 
 */
package com.security.core.social.interfaces;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author sca
 * 指定社交认证成功后处理器接口
 */
public interface SocialAuthenticationFilterPostProcessor {
	
	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}

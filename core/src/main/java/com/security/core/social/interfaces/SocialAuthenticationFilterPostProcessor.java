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

	/**
	 *  社交认证流程处理方法
	 * @param socialAuthenticationFilter
	 */
	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}

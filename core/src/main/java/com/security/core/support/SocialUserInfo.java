/**
 * 
 */
package com.security.core.support;

import lombok.Data;

/**
 * 社交用户信息
 * @author sca
 *
 */
@Data
public class SocialUserInfo {
	
	private String providerId;
	
	private String providerUserId;
	
	private String nickname;
	
	private String headimg;

}

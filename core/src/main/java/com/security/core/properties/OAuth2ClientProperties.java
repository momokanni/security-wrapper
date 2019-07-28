/**
 * 
 */
package com.security.core.properties;

import lombok.Data;

/**
 * @author sca
 * 认证客户端配置
 */
@Data
public class OAuth2ClientProperties {
	
	private String clientId;
	
	private String clientSecret;
	
	//令牌有效期
	private int accessTokenValiditySeconds;

}

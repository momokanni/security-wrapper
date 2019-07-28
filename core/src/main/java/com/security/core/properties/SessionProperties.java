/**
 * 
 */
package com.security.core.properties;

import lombok.Data;

/**
 * @author sca
 *
 */
@Data
public class SessionProperties {

	/**
	 * 用户在同一个系统中最大session数,默认：1
	 */
	private int maximumSessions = 1;
	/**
	 * 同一个账户：达到最大session时，是否阻止新的登录请求
	 * 默认: false
	 */
	private boolean maxSessionPreventsLogin;
	/**
	 * session失效时跳转的地址
	 */
	private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
}

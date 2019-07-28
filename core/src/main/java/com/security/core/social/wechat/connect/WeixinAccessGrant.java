/**
 * 
 */
package com.security.core.social.wechat.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author sca
 * 微信返回信息，于标准OAuth2不同，微信在获取access_token的同时返回openId,
 * 并没有单独的通过access_token获取openId的服务
 * 
 * 所以在这继承了标准的AccessGrant,添加了openId字段，作为对“获取access_token”返回信息的封装
 */
public class WeixinAccessGrant extends AccessGrant {

	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Getter
	@Setter
	private String openId;
	
	public WeixinAccessGrant(String accessToken) {
		super("");
	}
	
	public WeixinAccessGrant(String accessToken,String scope,String refreshToken,Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}
}

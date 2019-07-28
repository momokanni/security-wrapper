/**
 * 
 */
package com.security.core.properties;

import lombok.Data;

/**
 * @author sca
 * 微信配置项
 */
@Data
public class WeixinProperties extends BaseSocialProperties{
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";
}

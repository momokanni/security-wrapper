/**
 * 
 */
package com.security.core.social.wechat.api;

import lombok.Data;

/**
 * @author sca
 */
@Data
public class WeixinUserInfo {
	
	private String openId;
	
	private String nickName;
	
	private String language;
	
	private String sex;
	
	private String province;
	
	private String city;
	
	private String country;
	
	private String headimgurl;
	
	private String[] privilege;
	
	private String unionid;
}

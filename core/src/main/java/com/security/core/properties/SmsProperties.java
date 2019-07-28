package com.security.core.properties;

import lombok.Data;

@Data
public class SmsProperties {

	private int length = 6;
	
	private int expireIn = 60;
	
	/**
	 * 需要加入短信验证码验证的路径
	 */
	private String url;
}

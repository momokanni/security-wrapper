package com.security.core.properties;

import lombok.Data;

/**
 * @Description 短信配置类
 * @Author sca
 * @Date 2019-08-03 17:57
 **/
@Data
public class SmsProperties {

	private int length = 6;
	
	private int expireIn = 60;
	
	/**
	 * 需要加入短信验证码验证的路径
	 */
	private String url;
}

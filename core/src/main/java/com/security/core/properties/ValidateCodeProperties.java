package com.security.core.properties;

import lombok.Data;

/**
 * @Description 验证码属性类
 * @Author sca
 * @Date 2019-07-24 11:45
 **/
@Data
public class ValidateCodeProperties {

	private ImageCodeProperties imgCode;
	
	private SmsProperties smsCode; 

}

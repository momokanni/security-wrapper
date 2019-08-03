package com.security.core.validator.sms;

/**
 * @Description 发送短信验证码接口
 * @Author sca
 * @Date 2019-08-03 17:55
 **/
public interface SmsCodeSender {

	/**
	 * 发送短信验证码
	 * @param tel
	 * @param code
	 */
	void send(String tel, String code);
}

package com.security.core.validator.sms;


public interface SmsCodeSender {
	
	void send(String tel, String code);
}

package com.security.core.validator.sms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {
	
	public void send(String tel,String code) {
		log.info("telphone number : {} , code: {}" , tel , code);
	}


}

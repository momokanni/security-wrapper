package com.security.core.validator.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 自定义短信发送类
 * @Author sca
 * @Date 2019-08-03 17:07
 **/
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String tel,String code) {
		log.info("mobile number : {} , code: {}" , tel , code);
	}


}

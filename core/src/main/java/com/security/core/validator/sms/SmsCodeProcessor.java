package com.security.core.validator.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.processor.AbstractValidateCodeProcessor;

@Component(value = "smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
	
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String tel = ServletRequestUtils.getRequiredStringParameter( request.getRequest(), "tel");
		smsCodeSender.send(tel, validateCode.getCode());
	}

}

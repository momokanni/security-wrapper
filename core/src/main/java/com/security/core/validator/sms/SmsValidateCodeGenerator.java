package com.security.core.validator.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.properties.SecurityProperties;
import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.inteface.ValidateCodeGenerator;

/**
 * 短信验证码生成
 * @author sca
 *
 */
@Component(value = "smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
	
	@Autowired
	private SecurityProperties securityPro;
	
	@Override
	public ValidateCode generate(ServletWebRequest req) {
		String code = RandomStringUtils.randomNumeric(securityPro.getCode().getSmsCode().getLength());
		return new ValidateCode(code, securityPro.getCode().getSmsCode().getExpireIn());
	}

	public SecurityProperties getSecurityPro() {
		return securityPro;
	}

	public void setSecurityPro(SecurityProperties securityPro) {
		this.securityPro = securityPro;
	}
	
}

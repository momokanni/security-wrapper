package com.security.core.validator.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * 验证码异常处理
 * @author sca
 *
 */
public class ValidateCodeException extends AuthenticationException{

	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}

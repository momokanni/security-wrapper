package com.security.exception;

import com.security.web.enums.ResultEnums;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @Description 身份认证异常类
 * @Author sca
 * @Date 2019-08-03 17:58
 **/
@Getter
public class UserException extends AuthenticationException {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private int code;

	public UserException(ResultEnums resultEnums) {
		super(resultEnums.getMsg());
		this.code = resultEnums.getCode();
	}

	public UserException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}

package com.security.exception;

import com.security.web.enums.ResultEnums;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityCoreVersion;

@Getter
public class UserExpection extends AuthenticationException {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private int code;

	public UserExpection(ResultEnums resultEnums) {
		super(resultEnums.getMsg());
		this.code = resultEnums.getCode();
	}

	public UserExpection(int code,String msg) {
		super(msg);
		this.code = code;
	}
}

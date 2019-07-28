package com.security.core.validator.entity;

import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码父类
 * @author sca
 *
 */
@Data
public class ValidateCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 过期时间
	 */
	private LocalDateTime expireTime;

	public ValidateCode() {}

	public ValidateCode(String code, int expireIn) {
		this.code = code;
		//当前日期+保存多少秒
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code,LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpired() {
		
		return LocalDateTime.now().isAfter(expireTime);
	}
}

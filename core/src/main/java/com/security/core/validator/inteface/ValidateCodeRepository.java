package com.security.core.validator.inteface;

import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.enums.ValidateCodeType;

/**
 * 校验码存取器
 * @author sca
 *
 */
public interface ValidateCodeRepository {
	
	/**
	 * 保存验证码
	 * @param request
	 * @param code
	 * @param codeType
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType);
	
	/**
	 * 获取验证码
	 * @param request
	 * @param codeType
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeType codeType);
	
	/**
	 * 移除
	 * @param request
	 * @param codeType
	 */
	void remove(ServletWebRequest request, ValidateCodeType codeType);
}

package com.security.browser.session;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ValidateCode;
import com.security.core.validator.enums.ValidateCodeType;
import com.security.core.validator.inteface.ValidateCodeRepository;

/**
 * @author sca
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
	
	/**
	 * 存入session中code对应key的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	
	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	 
	
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType) {

		sessionStrategy.setAttribute(request, getSessionKey(request,codeType), code);
	}

	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
		return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, codeType));
	}

	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		
		sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
	}
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
		return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
	}

}

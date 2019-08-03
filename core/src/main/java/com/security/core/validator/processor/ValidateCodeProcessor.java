package com.security.core.validator.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description 验证码发送接口
 * @Author sca
 * @Date 2019-08-03 17:59
 **/
public interface ValidateCodeProcessor {
	
	/**
	 * 存入session中code对应key的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	
	/**
	 * 生成验证码方法
	 * ServletWebRequest ： spring工具类：封装request和response
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;
	
	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);
}

package com.security.core.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 工具类
 * @author sca
 *
 */
public class Tools {
	
	/**
	 * 通过截取请求URI，判断生成哪个验证码
	 * @param req
	 * @return
	 */
	public static String getProcessorType(ServletWebRequest req) {
		return StringUtils.substringAfter(req.getRequest().getRequestURI(), "/code/");
	}

}

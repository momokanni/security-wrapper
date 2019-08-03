package com.security.core.validator.inteface;

import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.validator.entity.ValidateCode;

/**
 * @Description 验证码生成接口
 * @Author sca
 * @Date 2019-08-03 17:59
 **/
public interface ValidateCodeGenerator {

	/**
	 * 验证码生成
	 * @param req
	 * @return
	 */
	ValidateCode generate(ServletWebRequest req);
}

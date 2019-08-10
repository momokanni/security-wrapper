package com.security.web.generator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.validator.entity.ImageCode;
import com.security.core.validator.inteface.ValidateCodeGenerator;

/**
 * 实现验证码生成逻辑的可配置化
 * 通过实现接口ValidateCodeGenerator，并通过组件声明的形式，覆盖掉security-core包中默认的生成逻辑
 * @author sunLin
 *
 */
// @Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
	
	
	@Override
	public ImageCode generate(ServletWebRequest req) {
		System.out.println("这是一个很高级的图片验证码");
		return null;
	}

}

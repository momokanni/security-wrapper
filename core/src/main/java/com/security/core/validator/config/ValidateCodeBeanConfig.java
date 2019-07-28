package com.security.core.validator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.security.core.properties.SecurityProperties;
import com.security.core.validator.image.ImageValidateCodeGenerator;
import com.security.core.validator.inteface.ValidateCodeGenerator;
import com.security.core.validator.sms.DefaultSmsCodeSender;
import com.security.core.validator.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {

	@Autowired
	private SecurityProperties sp;

	/**
	 * 这里有一个思想误区：
	 * 		弄清本地@Bean实例化有无必要的条件：1、实例是更改配置。 2、修改接口实现
	 * 
	 * 注：默认的实现就是要被覆盖的
	 * 		
	 * @return
	 */
	@Bean
	//该注解，第一种方式： 再实例化该对象时，首先检查是否已创建名为：imageValidateCodeGenerator的实例。
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator () {
		ImageValidateCodeGenerator icg = new ImageValidateCodeGenerator();
		icg.setSecurityProperties(sp);
		return icg;
	}
	
	@Bean
	//该注解，第二种：查找是否已存在 SmsCodeSender这个接口的实现
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeGenerator () {
		
		return new DefaultSmsCodeSender();
	}
}

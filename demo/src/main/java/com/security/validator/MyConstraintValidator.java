package com.security.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.security.service.DemoUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 自定义注解，校验类
 * @Author sca
 * @Date 2019-08-03 17:54
 **/
@Slf4j
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

	@Autowired
	private DemoUserService demoUserService;
	
	/**
	 * 初始化
	 */
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		log.info("Hi，这里是自定义注解，初始化方法");
	}

	/**
	 * 校验方法
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("isValid value: {} , context: {}" , value,context);
		demoUserService.doSomething("Tom");
		log.info("Hi, 这里是自定义注解处理方法，收到的属性值为: " + value);
		return false;
	}

}

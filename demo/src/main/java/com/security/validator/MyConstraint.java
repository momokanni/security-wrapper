package com.security.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

// @Target指明该注解可以标注在哪些地方
@Target({ElementType.METHOD, ElementType.FIELD})
// 指明该注解在运行时发挥作用
@Retention(RetentionPolicy.RUNTIME)
// 指明注解执行校验逻辑的类
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
	
	String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

	Class<?>[] groups() default { }; // TODO

	Class<? extends Payload>[] payload() default { }; // TODO

}

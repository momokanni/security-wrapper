package com.security.web.aspect;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 注解丰富，拦截到控制类，拦截粒度更精确，能获取参数
 * 请求拦截优先级：Filter > Interceptor > ControllerAdvice > Aspect > controller
 * @author sca
 *
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {
	
	/**
	 * 第一个 * 是任意返回值
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.security.web.controller.DemoUserController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		
		log.info("time aspect start");
		
		Object[] args = pjp.getArgs();
		for (Object arg : args) {
			log.info("aspect get request arg is {} ",arg);
		}
		
		long start = System.currentTimeMillis();
		
		// 调用后续处理器 -- 进入被调用方法
		Object object = pjp.proceed();
		
		log.info("aspect time cosuming: {}" , (System.currentTimeMillis() - start));
		
		log.info("aspect time end");
		
		return object;
	}
}

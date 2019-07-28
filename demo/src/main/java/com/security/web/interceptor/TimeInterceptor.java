package com.security.web.interceptor;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * && Filter区别：
 * 		1、Filter方法都集中在doFilter方法中。
 * 		2、Interceptor分出3个方法，方法执行前、后各有方法可以进行处理
 * 		3、Inteceptor能准确获知目标类、目标方法
 * 		4、拦截器 拦截所有控制器的方法调用
 * Inteceptor缺点：
 * 		1、获取不到参数值
 * @author sca
 * 声明成组件还要结合WebConfig 继承 WebMvcConfigurerAdapter 重写: addInterceptors();
 * 这样拦截器才会起作用
 */
@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		log.info("interceptor preHandle ...");
		log.info("current request class: {}, method name: {}" , ((HandlerMethod) handler).getBean().getClass().getName() , ((HandlerMethod) handler).getMethod().getName());
		request.setAttribute("start", new Date().getTime()); // --> 在请求request中添加start参数，用以在三个方法间传值
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView modelView) throws Exception {
		log.info("request startTime: {}" , request.getAttribute("start"));
		if (request.getAttribute("start") == null) {
			return;
		}
		log.info("interceptor postHandle ...");
		log.info("requst time cosuming: {}" , (new Date().getTime() - Long.parseLong(request.getAttribute("start").toString())));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) throws Exception {
		log.info("interceptor afterCompletion ...");
		log.info("ex is : {} " , ex);
	}
}

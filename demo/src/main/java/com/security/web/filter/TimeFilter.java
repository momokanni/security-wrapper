package com.security.web.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 能拿到http请求request和响应response信息
 * 但是无法获知处理该http请求的controller及其方法
 * 
 * Filter基于J2EE
 * @author sca
 *
 */

/**
 * @Description
 * @Author sca
 * @Date 2019-08-03 17:45
 * @Component 注释掉该注解 意在模拟第三方拦截器 配合WebConfig，将第三方拦截器加入到项目中
 **/
public class TimeFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("time filter init...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("time filter start...");
		long start = System.currentTimeMillis();
		// 
		chain.doFilter(request, response);
		logger.info("time cosuming: {}" , (System.currentTimeMillis() - start));
		logger.info("time filter finish...");
	}

	@Override
	public void destroy() {
		logger.info("time filter destory...");
	}

}

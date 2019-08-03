package com.security.web.config;

import java.util.ArrayList;
import java.util.List;
import com.security.web.filter.TimeFilter;
import com.security.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 配置类
 * @Author sca
 * @Date 2019-08-03 18:00
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	//spring对异步的支持 ~~~遗留问题（线程池）
//	@Override
//	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//		super.configureAsyncSupport(configurer);
//	}

	@Autowired
	private TimeInterceptor timeInterceptor;

	/**
	 * 同步注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns()
				.excludePathPatterns("/index.html","/*","/resources/*","/loginout");
	}
	
	/**
	 * 通过JavaConfig Bean的方式添加第三方Filter Bean到项目容器中
	 * @return
	 */
	@Bean
	public FilterRegistrationBean timeFilter() {
		//Filter注册类
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		//假设：第三方Filter 没有使用@Component注解
		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);

		//拦截路径集合
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		registrationBean.setUrlPatterns(urls);
		return registrationBean;
	}
}

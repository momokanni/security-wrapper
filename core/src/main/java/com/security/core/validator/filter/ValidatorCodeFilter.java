package com.security.core.validator.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;
import com.security.core.validator.enums.ValidateCodeType;
import com.security.core.validator.exception.ValidateCodeException;
import com.security.core.validator.processor.ValidateCodeProcessorHolder;

/**
 * 验证码校验（图片验证码，短信验证码）过滤器
 * OncePerRequestFilter :确保在一次请求只通过一次filter，而不需要重复执行
 * @author sca
 *
 */
@Slf4j
@Component("validateCodeFilter")
public class ValidatorCodeFilter extends OncePerRequestFilter implements InitializingBean {
	
	/**
	 * 验证码校验失败处理器
	 */
	@Getter
	@Setter
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	/**
	 * 系统配置信息
	 */
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 系统中的校验码处理器
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateProcessorHolder;
	
	/**
	 * 存放所有需要校验验证码的url，最终的目的还是要通过URL判断验证码类型
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	/**
	 * 初始化要拦截的url配置信息
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImgCode().getUrl(),ValidateCodeType.IMAGE);
		
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSmsCode().getUrl(),ValidateCodeType.SMS);
	}
	
	/**
	 * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
	 * 
	 * @param urlString
	 * @param type
	 */
	public  void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}
	
	/**
	 * 过滤的具体实现
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		log.info("validatorCode Filter Request URL : {}" , req.getRequestURI());
		//通过验证类型ValidateCodeType,来确定是图片验证还是短信验证
		//1、获取请求验证类型
		ValidateCodeType type = getValidateCodeType(req);
		//2、对类型进行判断
		if(type != null) {
			log.info("校验请求：{} 中的验证码，验证码类型: {}" ,req.getRequestURI(), type);
			try {
				// 3、通过validateCodeType获取对应的Processor进行验证处理
				validateProcessorHolder.findProcessorByType(type).validate(new ServletWebRequest(req));
				log.info("{}验证码校验通过" , type.getTypeName());
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(req, resp, exception);
				return;
			}
		}
 		chain.doFilter(req, resp);
	}
	
	/**
	 * 通过路径遍历比对获取类型
	 * @param req
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest req) {
		String requestType = "get";
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(req.getMethod(), requestType)) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				// 路径匹配
				if (pathMatcher.match(url, req.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
}

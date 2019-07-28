package com.security.browser.session;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.support.SimpleResponse;
import com.security.core.properties.SecurityProperties;

/**
 * @author sca
 * 抽象的session失效处理器
 */
@Slf4j
public class AbstractSessionStrategy {
	
	/**
	 * 跳转URL
	 */
	private String destinationUrl;
	
	private SecurityProperties securityProperties;
	
	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/**
	 * 跳转前是否创建新的session
	 */
	@Setter
	private boolean createNewSession = true;
	
	private ObjectMapper objMap = new ObjectMapper();
	
	public AbstractSessionStrategy(SecurityProperties securityProperties) {
		String invalidSessionUrl = securityProperties.getBrowser().getSession().getSessionInvalidUrl();
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),"url must start with '/' or with 'http(s) '");
		Assert.isTrue(StringUtils.endsWithIgnoreCase(invalidSessionUrl,".html"),"url must end with '.html'");
		
		this.destinationUrl = invalidSessionUrl;
		this.securityProperties = securityProperties;
	}
	
	
	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		log.info("session失效");
		
		if(createNewSession) {
			request.getSession();
		}
		
		String sourceUrl = request.getRequestURI();
		String targetUrl;
		
		if(StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			if(StringUtils.equals(sourceUrl, securityProperties.getBrowser().getLoginPage())) {
				
				targetUrl = sourceUrl;
			} else {
				targetUrl = destinationUrl;
			}
			log.info("session失效跳转路径: {}" , targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);
		} else {
			Object result = buildResponseContent(request);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objMap.writeValueAsString(result));
		}
	}

	protected Object buildResponseContent(HttpServletRequest request) {
		StringBuilder msg = new StringBuilder();
		msg.append("Session已失效");
		if(isConcurrency()) {
			msg.append(",有可能是并发导致的");
		}
		return new SimpleResponse(msg);
	}

	protected boolean isConcurrency() {
		
		return false;
	}
	
}

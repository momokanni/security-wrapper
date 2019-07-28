package com.security.browser.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.security.core.properties.SecurityProperties;

/**
 * 默认的session失效处理策略
 * @author sca
 *
 */
public class InvalidSessionStrategy extends AbstractSessionStrategy
		implements org.springframework.security.web.session.InvalidSessionStrategy {

	
	public InvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);

	}
	
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		onSessionInvalid(request, response);
	}

}

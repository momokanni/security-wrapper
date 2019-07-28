package com.security.browser.logout;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.support.SimpleResponse;

/**
 * 能接受到退出成功的请求，并做进一步处理
 * @author sca
 *
 */
@Slf4j
public class LoginOutSuccessHandler implements LogoutSuccessHandler {
	
	private String logoutUrl;
	
	private ObjectMapper objMap = new ObjectMapper();
	
	
	public LoginOutSuccessHandler(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.info("退出成功");
		
		if(StringUtils.isBlank(logoutUrl)) {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(objMap.writeValueAsString(new SimpleResponse("退出成功。")));
		} else {
			response.sendRedirect(logoutUrl);
		}
	}

}

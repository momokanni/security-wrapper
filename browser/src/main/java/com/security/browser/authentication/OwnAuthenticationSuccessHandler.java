package com.security.browser.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.properties.LoginResponseType;
import com.security.core.properties.SecurityProperties;

/**
 * 认证成功
 * @author sca
 *
 * SavedRequestAwareAuthenticationSuccessHandler （springSecurity默认成功处理器）
 */
@Slf4j
@Component(value = "ownAuthenticationSuccessHandler")
public class OwnAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	/**
	 * 将Authentication转换成json的工具类
	 */
	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * param : Authentication【封装的认证信息】
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		log.info("登录成功");
		
		//判断登录方式
		if(LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("apllication/json;charset=UTF-8");
			response.getWriter().write(objMapper.writeValueAsString(authentication));
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
}

package com.security.app.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.app.social.AppSignUpUtils;
import com.security.core.social.SocialController;
import com.security.core.support.SocialUserInfo;
import com.security.core.properties.SecurityConstants;
import org.springframework.http.HttpStatus;

/**
 * @Description 
 * @Author sca
 * @Date 2019-07-24 11:32
 **/
@RestController
public class AppSecurityController extends SocialController{
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@Autowired
	private AppSignUpUtils appSignUpUtils;
	
	/**
	 * 
	 * @param request
	 * @return
	 * app不支持为什么还在用providerSignInUtils
	 * 答：因为每次请求都会新建一个session，但第二请求会覆盖上一个请求session
	 *    所以数据还是要先从session中取出来放入redis中
	 *    
	 * 结果：返回给app 401状态码，引导用户跳转到app注册页
	 * 
	 */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
		return buildSocialUserInfo(connection);
	}
}

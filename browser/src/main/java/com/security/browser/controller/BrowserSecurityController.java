package com.security.browser.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import com.security.core.support.SimpleResponse;
import com.security.core.support.SocialUserInfo;
import com.security.core.properties.SecurityConstants;
import com.security.core.properties.SecurityProperties;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:55
 **/
@Slf4j
@RestController
public class BrowserSecurityController {

	/**
	 * 当需要身份认证跳转到该controller前，springSecurity会把请求插入到【请求缓存】中
	 * 身份认证方法可从该类中拿到请求
 	 */
	private RequestCache requesetCache = new HttpSessionRequestCache();
	/**
	 * spring 跳转策略 工具
	 */
	private RedirectStrategy redirect = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityPro;
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	/**
     * 【身份认证】
     * 场景: 将需要身份认证的请求引导至该方法，方法内部判断其请求 是否是html请求引发的跳转
     *      是：返回登录页。否：返回401
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requesetCache.getRequest(request, response);
		if (savedRequest != null) {
			String target = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是："+target);
			if(StringUtils.endsWith(target, ".html")) {
				redirect.sendRedirect(request, response, securityPro.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问该地址需要身份认证，请引导用户至登录页。");
	}
	
	/**
	 * 这个方法的目的：
	 * 场景： 当非注册用户点击QQ登录，认证不通过，跳转至“注册/绑定”页，在该页显示用户登录QQ的基本信息
	 * 	          体验更友好。
	 * @param request
	 * @return
	 */
	@GetMapping("/social/user")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo sui = new SocialUserInfo();
		
		// providerSignInUtils从session中获取的social信息
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		// 注： connection.getKey() 源码对于connectionData的分解 挺有意思
		sui.setProviderId(connection.getKey().getProviderId());
		sui.setProviderUserId(connection.getKey().getProviderUserId());
		sui.setNickname(connection.getDisplayName());
		sui.setHeadimg(connection.getImageUrl());
		return sui;
	}
	
	/**
	 * session失效后的处理
	 * @return
	 */
	@GetMapping("/session/invalid")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse sessionTimeOutMsg() {
		String msg = "session过期";
		
		return new SimpleResponse(msg);
	}
}

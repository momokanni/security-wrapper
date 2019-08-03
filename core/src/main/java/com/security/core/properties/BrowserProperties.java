package com.security.core.properties;

import lombok.Data;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:55
 **/
@Data
public class BrowserProperties {
	
	/**
	 * session管理配置项
	 */
	private SessionProperties session = new SessionProperties();
	/**
	 * 指定登录页面
	 */
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	/**
	 * 登出请求路径
	 */
	private String loginOutUrl;
	
	/**
	 * 记住我 功能的有效时间，默认1小时
	 */
	private int rememberMeSeconds = 3600;
	
	/**
	 * 注册页
	 */
	private String signUp = "/signUp.html";
	
	/**
	 * 登录响应的方式，默认是json
	 */
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	/**
	 * 登录成功后跳转的地址
	 * 只在signInResponseType为REDIRECT时生效
	 */
	private String signInSuccessUrl;
	/**
	 * 静态资源不拦截路径
	 */
	private String staticAvoidPath;
}

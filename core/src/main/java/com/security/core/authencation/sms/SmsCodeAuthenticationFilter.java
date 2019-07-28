package com.security.core.authencation.sms;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import com.security.core.properties.SecurityConstants;

/**
 * 短信验证码  登录验证过滤器
 * @author sca
 * 仿造UsernamePasswordAuthenticationFilter 创建 短信验证码Filter
 *
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private String tel = SecurityConstants.DEFAULT_PARAMETER_NAME_TEL;
	
	//只接受post请求
	private boolean postOlny = true;
	
	public SmsCodeAuthenticationFilter() {
		
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS , "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse rep) throws AuthenticationException, IOException, ServletException {
		
		if(postOlny && !req.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
		}
		
		String telNum = obtainTel(req);
		
		if (telNum == null) {
			telNum = "";
		}

		telNum = telNum.trim();
		
		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(telNum);
		
		setDetail(req,authRequest);
		
		//通过AuthenticationManager管理的所有provider去找到对应处理的provider
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	//将请求详情信息set到SmsCodeAuthenticationToken
	protected void setDetail(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
		
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * 获取手机号
	 * @param req
	 * @return
	 */
	private String obtainTel(HttpServletRequest req) {

		return req.getParameter(tel);
	}


	public void setPostOlny(boolean postOlny) {
		this.postOlny = postOlny;
	}

	public void setTel(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.tel = usernameParameter;
	}
	
	public String getTel(String usernameParameter) {
		return tel;
	}

}

package com.security.app.authentication.openId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import com.security.core.properties.SecurityConstants;

/**
 * @author sca
 * 简化认证模式： 通过第三方登录的openid 来获取本系统的token
 */
public class OpenidAuthenticationFilter extends AbstractAuthenticationProcessingFilter  {

	private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
	private String providerParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
	private boolean postOnly = true;
	
	public OpenidAuthenticationFilter() {
		super( new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID, "POST") );
	}
	
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) {
		
		if(postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported" + request.getMethod());
		}
		
		String openid = obtainOpenId(request);
		String providerId = obtainProviderId(request);
		
		if(openid == null) {
			openid = null;
		}
		if(providerId == null) {
			providerId = null;
		}
		
		openid = openid.trim();
		providerId = providerId.trim();
		
		OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openid, providerId);
		
		//Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	private String obtainProviderId(HttpServletRequest request) {
		
		return request.getParameter(openIdParameter);
	}

	private String obtainOpenId(HttpServletRequest request) {

		return request.getParameter(providerParameter);
	}

	
	public String getOpenIdParameter() {
		return openIdParameter;
	}
	
	public void setOpenIdParameter(String openIdParameter) {
		Assert.hasText(openIdParameter, "Username parameter must not be empty or null");
		this.openIdParameter = openIdParameter;
	}

	public String getProviderParameter() {
		return providerParameter;
	}

	public void setProviderParameter(String providerParameter) {
		this.providerParameter = providerParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	
}

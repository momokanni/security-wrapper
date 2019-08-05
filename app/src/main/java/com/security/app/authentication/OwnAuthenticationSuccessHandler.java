package com.security.app.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.properties.LoginResponseType;
import com.security.core.properties.SecurityProperties;

/**
 * 认证成功
 * @author sca
 * 流程描述：
 * 		登录请求 --> XXXFilter -->登录处理过程
 *      --> AuthenticationSUccessHandler{
 *      		<1>.clientDetails: 
 *      				从请求头Authorization中获取clientId & clientSecret  数据格式：Authorization：Basic aW1vb2M6aW1vb2NTZWNyZXQ=
 *      				clientDetailsService.loadClientByClientId(clientId);
 *      		<2>.TokenRequest: 
 *      				将查询到的clientDetails作为实例化参数，实例出TokenRequest，其中参数grant_type='custom'(自定义) 而不是授权码模式或者密码模式
 *      		<3>.OAuth2Request:
 *      				clientDetails + TokenRequest = OAuth2Request
 *      		<4>.OAuth2Authentication:
 *      				OAuth2Request + Authentication(成功登录用户信息) = OAuth2Authentication
 *      		<5>.OAuth2AccessToken: 
 *      				AuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);
 *      		<6>.Response以json格式输出给前端		
 *      } 
 */
@Component(value = "ownAuthenticationSuccessHandler")
public class OwnAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ClientDetailsService clientDetailsService;

	/**
	 * TokenEnhancer是令牌增强器，可定制token
	 * TokenStore定制令牌存取
	 */
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		/** basicAuthenticationFilter 提供了解析请求头的部分代码 **/
		String header = request.getHeader("Authorization");

		String keyWord = "Basic";
		if(header == null || !header.startsWith(keyWord)) {
			
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}
		
		String[] tokens = extractAndDecodeHeader(header);
		assert tokens.length == 2;
		
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		if(clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId: "+clientId +",对应的配置信息不存在。");
		} else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配，clientId: "+clientId);
		}
		
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
		
		OAuth2Request oauth2Request = tokenRequest.createOAuth2Request(clientDetails);
		
		//包含能够确认三方客户端和用户、认证模式的请求参数
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oauth2Request, authentication);
		
		OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		
		response.setContentType("apllication/json;charset=UTF-8");
		response.getWriter().write(objMapper.writeValueAsString(accessToken));
	}
	
	
	private String[] extractAndDecodeHeader(String header) throws IOException {
		
		// POST请求 Authorization：Basic aW1vb2M6aW1vb2NTZWNyZXQ=
		byte[] base64Token = header.substring(6).getBytes();
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (Exception e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}
		
		// txhl(clientID) : txhlSecret(clientSecret)
		String token = new String(decoded,"UTF-8");
		
		int delim = token.indexOf(":");
		
		if(delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		
		return new String[] {token.substring(0, delim),token.substring(delim + 1)};
	}
	
}

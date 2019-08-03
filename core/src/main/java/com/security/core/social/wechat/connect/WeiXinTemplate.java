package com.security.core.social.wechat.connect;

import java.nio.charset.Charset;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author sca
 *
 */
@Slf4j
public class WeiXinTemplate extends OAuth2Template {
	
	private String clientId;
	
	private String clientSecret;
	
	private String accessTokenUrl;
	
	private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	public WeiXinTemplate(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//这个值为true时，OAuth2Template请求才会将client_id(appId)和client_secret(appSecret)这俩参数附带上
		setUseParametersForClientAuthentication(true);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.accessTokenUrl = accessTokenUrl;
	}
	
	/**
	 * 拼装获取access_token的url
	 */
	@Override
	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
			MultiValueMap<String, String> additionalParameters) {
		
		StringBuilder tokenUrl = new StringBuilder(accessTokenUrl);
		tokenUrl.append("?appid="+clientId);
		tokenUrl.append("&secret="+clientSecret);
		tokenUrl.append("&code="+authorizationCode);
		tokenUrl.append("&grant_type=authorization_code");
		return getAccessToken(tokenUrl);
	}
	
	
	/**
	 * 刷新access_token
	 */
	@Override
	public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
		
		StringBuilder tokenUrl = new StringBuilder(REFRESH_TOKEN_URL);
		tokenUrl.append("?appid="+clientId);
		tokenUrl.append("&grant_type=authorization_code");
		tokenUrl.append("&refresh_token="+refreshToken);
		return getAccessToken(tokenUrl);
	}
	
	
	/**
	 * 执行获取access_token的请求，并对返回值封装到WeixinAccessGrant中
	 * @param tokenUrl
	 * @return
	 */
	private AccessGrant getAccessToken(StringBuilder tokenUrl) {
		log.info("获取access_token, 请求URL: {}" , accessTokenUrl.toString());
		
		String response = getRestTemplate().getForObject(tokenUrl.toString(), String.class);
		log.info("获取access_token, 响应内容: {}" , response);
		
		Map<String, Object> result = null;
		try {
			result = new ObjectMapper().readValue(response, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))) {
			String errCode = MapUtils.getString(result, "errcode");
			String errMsg = MapUtils.getString(result, "errmsg");
			throw new RuntimeException("获取access token失败, errcode:" + errCode + ", errmsg:" + errMsg);
		}
		
		WeiXinAccessGrant wxGrant = new WeiXinAccessGrant(
										MapUtils.getString(result, "access_token"),
										MapUtils.getString(result, "scope"),
										MapUtils.getString(result, "refresh_token"),
										MapUtils.getLong(result, "expires_in")
									);
		wxGrant.setOpenId(MapUtils.getString(result, "openid"));
		return wxGrant;
	}
	
	
	/**
	 * 构建获取授权码的请求
	 */
	@Override
	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		StringBuilder url = new StringBuilder(super.buildAuthenticateUrl(parameters));
		url.append("&appid=").append(clientId).append("&scope=snsapi_login");
		return url.toString();
	}
	
	@Override
	public String buildAuthorizeUrl(OAuth2Parameters parameters) {

		return buildAuthenticateUrl(parameters);
	}
	
	/**
	 * 微信返回的contentType是html/text，添加相应的HttpMessageConverter进行处理。
	 */
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}

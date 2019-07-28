package com.security.core.social.qq.connect;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *  自定义qq认证模板
 * @author sca
 *
 */
@Slf4j
public class QQAuth2Template extends OAuth2Template {

	public QQAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//这个值为true时，OAuth2Template请求才会将client_id(appId)和client_secret(appSecret)这俩参数附带上
		/**
		 *  源码OAuth2Template: exchangeForAccess()方法内有判断
		 *  if (useParametersForClientAuthentication) {
				params.set("client_id", clientId);
				params.set("client_secret", clientSecret);
			}
		 */
		setUseParametersForClientAuthentication(true);
	}
	
	/**
	 * 获取token
	 * 源码: 
	 * 		extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
	 * 		extractAccessGrant的传参：将请求返回值转换成Map类型，createAccessGrant(..)再从参数Map中获取数据用以创建AccessGrant
	 * 		QQ返回的是字符串所以要重写postForAccessGrant，直接在该方法内获取返回值，并创建AccessGrant并返回。
	 * @param accessTokenUrl
	 * @param parameters
	 * @return 根据QQ标准做了一个自定义解析
	 */
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		log.info("QQ获取access_token的响应："+responseStr);
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expireIn = Long.parseLong(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		return new AccessGrant(accessToken, null, refreshToken, expireIn);
	}
	
	/**
	 *  发送请求模板添加解析HTML解析器
	 *  contentType: "text/html"
	 * @return
	 */
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate template = super.createRestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return template;
	}
}

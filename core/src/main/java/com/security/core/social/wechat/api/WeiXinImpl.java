/**
 * 
 */
package com.security.core.social.wechat.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * scope为Request的Spring bean, 根据当前用户的accessToken创建。
 * @author sca
 *
 */
public class WeiXinImpl extends AbstractOAuth2ApiBinding implements WeiXin {

	private ObjectMapper objMapper = new ObjectMapper();

	/**
	 * 获取用户信息
	 */
	private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";
	
	public WeiXinImpl(String accessToken) {
		super(accessToken,TokenStrategy.OAUTH_TOKEN_PARAMETER);
	}
	
	/**
	 * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，
	 * 微信返回的是UTF-8，所以覆盖原来的方法。
	 */
	@Override
	public List<HttpMessageConverter<?>> getMessageConverters(){
		
		List<HttpMessageConverter<?>> messageConverts = super.getMessageConverters();
		//移除原有的编码格式为：ISO-8859-1的StringHttpMessageConverter
		messageConverts.remove(0);
		//添加新的字符编码为UTF-8的StringHttpMessageConverter
		messageConverts.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return messageConverts;
	}
	
	
	/**
	 * 查询微信用户信息
	 */
	@Override
	public WeiXinUserInfo getUserInfo(String openId) {
		String url = URL_GET_USER_INFO + openId;
		String response = getRestTemplate().getForObject(url, String.class);
		String keyWord = "errcode";
		if(StringUtils.contains(response, keyWord)) {
			return null;
		}
		WeiXinUserInfo user = null;
		try {
			user = objMapper.readValue(response, WeiXinUserInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	

}

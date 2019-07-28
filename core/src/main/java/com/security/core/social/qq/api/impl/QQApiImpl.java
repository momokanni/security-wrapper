package com.security.core.social.qq.api.impl;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.core.social.qq.api.QQ;
import com.security.core.social.qq.entity.QQUserInfo;

/**
 * 获取QQ用户信息
 * @author sca
 *
 */
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQ {
	
	// 通过token获取openid
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
		
	//获取用户信息
	private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	@Getter
	@Setter
	private String appId; //应用ID

	@Getter
	@Setter
	private String openid;//QQ用户在该应用ID
	
	private ObjectMapper objmapper = new ObjectMapper();
	
	public QQApiImpl(String accessToken,String appId) {
		// 默认的策略是 AUTHORIZATION_HEADER 将参数加入到请求头中
		// 而QQ则是将token作为一个查询参数附带在请求路径上，所以这里需要更改策略为：ACCESS_TOKEN_PARAMETER
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		
		// 通过access_token获取openId
		String url = String.format(URL_GET_OPENID, accessToken);
		// 返回数据格式 查看QQ开放平台的开发文档
		String result = getRestTemplate().getForObject(url, String.class);
		
		this.openid = StringUtils.substringBetween(result, "\"openid\":\"","\"}");
	}
	
	
	@Override
	public QQUserInfo getQQUserInfo() {
		String url = String.format(URL_GET_USER_INFO, appId,openid);
		
		String result = getRestTemplate().getForObject(url, String.class);
		
		QQUserInfo userInfo = null;
		try {
			userInfo = objmapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openid);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败",e);
		}
	}
}

package com.security.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import com.security.core.social.qq.api.QQ;
import com.security.core.social.qq.api.impl.QQApiImpl;

/**
 * @author sca
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{

	private String appid;
	
	// 获取授权码地址 -- 将用户导向认证服务器的路径
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	// 申请令牌地址
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId,String appSecret) {
		super(new QQAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appid = appId;
	}

	@Override
	public QQ getApi(String accessToken) {

		return new QQApiImpl(accessToken, appid);
	}

}

/**
 * 
 */
package com.security.core.social.wechat.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import com.security.core.social.wechat.api.WeiXin;
import com.security.core.social.wechat.api.WeiXinImpl;

/**
 * @author sca
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 */
public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {
	
	/**
	 * 微信获取授权码的url
	 */
	private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
	/**
	 * 微信获取accessToken的url
	 */
	private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	
	public WeiXinServiceProvider(String appId, String appSecret) {
		super(new WeiXinTemplate(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
	}

	@Override
	public WeiXin getApi(String accessToken) {
		
		return new WeiXinImpl(accessToken);
	}

	

}

/**
 * 
 */
package com.security.core.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.security.core.social.wechat.api.Weixin;

/**
 * @author sca
 * 微信连接工厂
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {
	
	public WeixinConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new WeixinServiceProvider(appId,appSecret), new WeixinAdapter());
	}
	
	/**
	 * 由于微信的openId是和accessToken一起返回，
	 * 所以直接根据accessToken设置providerUserId即可，
	 * 不用像QQ那样通过QQAdapter来获取
	 */
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if(accessGrant instanceof WeixinAccessGrant) {
			return ((WeixinAccessGrant)accessGrant).getOpenId();
		}
		return null;
	}
	
	/**
	 * 为什么要覆盖createConnection方法：
	 * 		1、源码getAdapter()是单例
	 * 		2、QQAdapter 和 WeixinAdapter 不同点在于 WeixinAdapter要保存一个openid
	 * 		正因为微信用户的openid都不相同，所以每次请求需根据不同用户重新创建WeixinAdapter.
	 * 		QQ不管是获取openid 还是获取access_token都是在QQApiImpl中操作。
	 * 		所以QQ的Adapter可以是单例的
	 */
	@Override
	public Connection<Weixin> createConnection(AccessGrant accessGrant) {

		return new OAuth2Connection<Weixin>(getProviderId(), extractProviderUserId(accessGrant),
				accessGrant.getAccessToken(),accessGrant.getRefreshToken(),accessGrant.getExpireTime(),
				getOAuth2ServiceProvider(),getApiAdapter(extractProviderUserId(accessGrant)));
	}
	
	@Override
	public Connection<Weixin> createConnection(ConnectionData data) {

		return createConnection(data);
	}
	
	private ApiAdapter<Weixin> getApiAdapter(String providerUserId){
		return new WeixinAdapter(providerUserId);
	}
	
	private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider(){
		
		return (OAuth2ServiceProvider<Weixin>)getServiceProvider();
	}
}

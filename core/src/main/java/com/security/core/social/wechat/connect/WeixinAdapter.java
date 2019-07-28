/**
 * 
 */
package com.security.core.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.security.core.social.wechat.api.Weixin;
import com.security.core.social.wechat.api.WeixinUserInfo;

/**
 * 将微信 api的数据模型转为spring social的标准模型。
 * @author sca
 *
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {
	
	private String openId;
	
	public WeixinAdapter() {

	}
	
	public WeixinAdapter(String openId) {
		this.openId = openId;
	}
	
	
	@Override
	public UserProfile fetchUserProfile(Weixin arg0) {

		return null;
	}
	
	/**
	 * object trans
	 */
	@Override
	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserInfo profile = api.getUserInfo(openId);
		values.setDisplayName(profile.getNickName());
		values.setProviderUserId(profile.getOpenId());
		values.setImageUrl(profile.getHeadimgurl());
	}

	@Override
	public boolean test(Weixin arg0) {
		
		return true;
	}

	@Override
	public void updateStatus(Weixin arg0, String arg1) {
		
	}

}

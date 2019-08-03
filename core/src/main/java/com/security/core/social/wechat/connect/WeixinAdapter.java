/**
 * 
 */
package com.security.core.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.security.core.social.wechat.api.WeiXin;
import com.security.core.social.wechat.api.WeiXinUserInfo;

/**
 * 将微信 api的数据模型转为spring social的标准模型。
 * @author sca
 *
 */
public class WeixinAdapter implements ApiAdapter<WeiXin> {
	
	private String openId;
	
	public WeixinAdapter() {

	}
	
	public WeixinAdapter(String openId) {
		this.openId = openId;
	}
	
	
	@Override
	public UserProfile fetchUserProfile(WeiXin arg0) {

		return null;
	}
	
	/**
	 * object trans
	 */
	@Override
	public void setConnectionValues(WeiXin api, ConnectionValues values) {
		WeiXinUserInfo profile = api.getUserInfo(openId);
		values.setDisplayName(profile.getNickName());
		values.setProviderUserId(profile.getOpenId());
		values.setImageUrl(profile.getHeadimgurl());
	}

	@Override
	public boolean test(WeiXin arg0) {
		
		return true;
	}

	@Override
	public void updateStatus(WeiXin arg0, String arg1) {
		
	}

}

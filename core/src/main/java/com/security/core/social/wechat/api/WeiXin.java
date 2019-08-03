/**
 * 
 */
package com.security.core.social.wechat.api;

/**
 * 微信接口
 * @author sca
 *
 */
public interface WeiXin {

	/**
	 * 获取微信用户信息
	 * @param openId
	 * @return
	 */
	WeiXinUserInfo getUserInfo(String openId);
}

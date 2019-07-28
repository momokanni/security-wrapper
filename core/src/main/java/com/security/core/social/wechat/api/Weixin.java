/**
 * 
 */
package com.security.core.social.wechat.api;

/**
 * 微信接口
 * @author sca
 *
 */
public interface Weixin {

	WeixinUserInfo getUserInfo(String openId);
}

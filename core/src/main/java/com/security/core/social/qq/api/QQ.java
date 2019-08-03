package com.security.core.social.qq.api;

import com.security.core.social.qq.entity.QQUserInfo;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-03 17:28
 **/
public interface QQ {

	/**
	 * 获取QQ用户信息
	 * @return
	 */
	QQUserInfo getQQUserInfo();
}

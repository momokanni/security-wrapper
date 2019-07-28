package com.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import com.security.core.social.qq.api.QQ;
import com.security.core.social.qq.entity.QQUserInfo;

/**
 * 接口ApiAdapter：An adapter that bridges between the uniform {@link Connection} model and a specific provider API model.
 * @author sca
 *
 */
public class QQAdapter implements ApiAdapter<QQ> {

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		
		return null;
	}

	/**
	 * 在Connection和API之间起到数据适配的作用
	 * 通过该方法将服务商（Provider）个性化的数据转换封装到ConnectionValues标准的数据结构中
	 * @param connectionValues 包含创建Connection所需的数据项
	 */
	@Override
	public void setConnectionValues(QQ api, ConnectionValues connectionValues) {

		QQUserInfo userInfo = api.getQQUserInfo();
		connectionValues.setDisplayName(userInfo.getNickname());
		connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
		// 服务商用户ID
		connectionValues.setProviderUserId(userInfo.getOpenId());
		// 个人主页
		connectionValues.setProfileUrl(null);
	}

	/**
	 * 测试当前API是否可用
	 */
	@Override
	public boolean test(QQ api) {

		return true;
	}

	/**
	 * 有个人主页的三方，通过发送消息更新状态
	 * 例：通过在个人主页发送一条weibo
	 */
	@Override
	public void updateStatus(QQ api, String message) {
		// do nothing
	}

}

package com.security.social;

import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;

/**
 * 第三方登录 且是 未注册用户默认自动生成一个userInfo
 * @author Administrator
 * 
 */
@Component
public class ConnectionSignUp implements org.springframework.social.connect.ConnectionSignUp {
	
	/**
	 * 当从userConnections表中查询不到，同时ConnectionSignUp不为空
	 * 且excute(...)返回值不为空，则进行创建
	 * 
	 * 源码： JdbcUsersConnectionRepository --> findUserIdWithConnection
	 */
	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息 默认 创建用户,并返回用户唯一标识
		return connection.getDisplayName();
	}

}

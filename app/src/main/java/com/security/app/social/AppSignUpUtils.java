package com.security.app.social;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.security.app.exception.AppException;

/**
 * @author sca
 * 该类作用：将社交登录信息存放进redis中
 * 原因：app不支持session存储
 * 		
 */
@Component
public class AppSignUpUtils {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	/**
	 * 把App登录的用户信息save到redis
	 * @param request
	 * @param connectionData
	 */
	public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
		
		redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);
	}
	
	/**
	 * 将redis缓存的用户信息与 系统 "注册"或者"绑定"的用户信息进行绑定并存到数据库中
	 * @param request
	 * @param userId
	 */
	public void doPostSignUp(WebRequest request, String userId) {
		String key = getKey(request);
		if(!redisTemplate.hasKey(key)) {
			throw new AppException("user social information does not exist");
		}
		ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
		//connectionFactoryLocator 通过ProviderId(weixin/QQ)获取对应的ConnectionFactory
		//并根据缓存的用户社交账号信息生成对应的connection
		Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
								   .createConnection(connectionData);
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

		redisTemplate.delete(key);
	}

	/**
	 * 生成redis key
	 * @param request
	 * @return
	 */
	private String getKey(WebRequest request) {
		String deviceId = request.getParameter("deviceId");
		if(StringUtils.isBlank(deviceId)) {
			throw new AppException("device id not null");
		}
		
		return "security:social.connect."+deviceId;
	}
	
	
}

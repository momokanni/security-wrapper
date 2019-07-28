package com.security.app.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.security.core.properties.SecurityConstants;
import com.security.core.social.config.LocalSpringSocialConfigure;

/**
 * @author sca
 * spring容器在初始化之前和之后要做的操作
 */
public class SpringSocialConfigurePostProcessor implements BeanPostProcessor {

	/**
	 * 初始化之前：直接返回bean，不需要做任何操作
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 初始化后：修改signupUrl
	 * 认证未成功： 浏览器是跳转到注册页，app需要直接返回Json数据（引导用户注册），所以需要不同的跳转路径
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		if(StringUtils.equals(beanName, "socialSecurityConfig")) {
			LocalSpringSocialConfigure config = (LocalSpringSocialConfigure)bean;
			config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
			return config;
		}
		return bean;
	}

}

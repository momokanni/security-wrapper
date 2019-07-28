package com.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.security.core.properties.SecurityProperties;

/**
 * 并发登录导致session失效时，默认的处理策略
 * 记录session超时的具体原因，
 * 例：账号被谁挤下去，几点、IP之类的信息
 * @author sca
 */
public class ExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public ExpiredSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	/**
	 * 并发登录session超时的处理策略
	 * 参数：SessionInformationExpiredEvent  能获取session超时事件
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		
		onSessionInvalid(event.getRequest(),event.getResponse());
	}
	
	
	@Override
	protected boolean isConcurrency() {

		return true;
	}

}

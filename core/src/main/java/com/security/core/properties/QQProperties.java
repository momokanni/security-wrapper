package com.security.core.properties;

import lombok.Data;

/**
 * @author sca
 * 这里引用的SocialProperties是social源码包下提供的配置抽象类
 */
@Data
public class QQProperties extends BaseSocialProperties {

	// 服务提供商的默认标识
	private String providerId = "qq";
}

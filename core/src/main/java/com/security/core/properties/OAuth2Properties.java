package com.security.core.properties;

import lombok.Data;

/**
 * @author sca
 * 认证配置
 */
@Data
public class OAuth2Properties {
	
	private String jwtSigningKey = "txhl";
	
	private OAuth2ClientProperties[] clients = {};
	
	private String tokenStore = "redis";

	public OAuth2ClientProperties[] getClients() {
		return clients;
	}

}

package com.security.core.properties;

import lombok.Data;

import java.util.List;

/**
 * @author sca
 * 认证配置
 */
@Data
public class OAuth2Properties {
	
	private String jwtSigningKey = "txhl";
	
	private List<OAuth2ClientProperties> clients;

	private List<Object> str;
	
	private String tokenStore = "redis";

	public List<OAuth2ClientProperties> getClients() {
		return clients;
	}

}

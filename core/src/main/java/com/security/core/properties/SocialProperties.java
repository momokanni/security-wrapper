package com.security.core.properties;

import lombok.Data;

/**
 * @Description 社交属性类
 * @Author sca
 * @Date 2019-07-24 11:41
 **/
@Data
public class SocialProperties {
	
	private QQProperties qq = new QQProperties();
	
	private WeixinProperties weixin = new WeixinProperties();
	
	private String filterProcessUrl = "/auth";
}

 package com.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

 /**
 * @Description  配置类
 * @Author sca
 * @Date 2019-08-03 17:55
 **/
@Data
@ConfigurationProperties(prefix = "com.security")
public class SecurityProperties {

	/**
	 * 默认将配置文件中 com.security.browser【prefix + 配置类变量名】 开头的配置读取到BrowserProperties中
	 */
	private BrowserProperties browser = new BrowserProperties();
	
	/**
	 * 请求配置(url?参数=) > 应用配置 > 默认配置
	 * 默认将配置文件中 com.security.code【prefix + 配置类变量名】 开头的配置读取到ValidateCodeProperties中
	 */
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	/**
	 * 第三方配置
	 * 默认将配置文件中 com.security.social【prefix + 配置类变量名】 开头的配置读取到SocialProperties中
	 * @return
	 */
	private SocialProperties social = new SocialProperties();
	
	/**
	 * 认证配置
	 * 默认将配置文件中 com.security.oauth【prefix + 配置类变量名】 开头的配置读取到OAuth2Properties中
	 */
	private OAuth2Properties oauth = new OAuth2Properties();

}

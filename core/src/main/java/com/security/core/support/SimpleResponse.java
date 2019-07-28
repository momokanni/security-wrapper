package com.security.core.support;

import lombok.Data;

/**
 * 请求响应类
 * @author sca
 *
 */
@Data
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}
	
	private Object content;
}

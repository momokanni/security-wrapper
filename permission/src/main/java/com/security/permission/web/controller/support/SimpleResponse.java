/**
 * 
 */
package com.security.permission.web.controller.support;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 13:40
 **/
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}

	@Getter
	@Setter
	private Object content;
	
}

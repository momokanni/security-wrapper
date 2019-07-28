package com.security.core.properties;

import lombok.Data;

@Data
public class ImageCodeProperties {

	private int width;

	private int height;
	
	private int length = 4;
	
	private int expireIn = 60;
	
	private String url;
}

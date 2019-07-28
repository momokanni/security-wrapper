package com.security.web.entity;

import lombok.Data;

@Data
public class FileInfo {
	
	public FileInfo(String path) {
		this.path = path;
	}

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}

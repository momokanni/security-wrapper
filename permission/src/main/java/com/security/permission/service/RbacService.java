package com.security.permission.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author sca
 * @Date 2019-08-19 13:39
 **/
public interface RbacService {
	
	boolean hasPermission(HttpServletRequest request, Authentication authentication);

}

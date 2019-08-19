package com.security.permission.authentication;

import com.security.permission.domain.Admin;
import com.security.permission.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 
 * @Author sca
 * @Date 2019-08-19 11:10
 **/
@Slf4j
//@Component
@Transactional(rollbackFor = {Exception.class})
public class RbacUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("表单登录用户名: {}" , username);
		Admin admin = adminRepository.findByUsername(username);
		admin.getUrls();
		return admin;
	}

}

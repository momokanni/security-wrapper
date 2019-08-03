package com.security.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @description: 自定义UserDetailsService
 * @author: sca
 * @create: 2019-07-28 22:30
 **/
public interface SuperUserDetailsService extends UserDetailsService {

    /**
     * 通过手机号获取用户详情
     * @param tel
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByTel(String tel) throws UsernameNotFoundException;

}

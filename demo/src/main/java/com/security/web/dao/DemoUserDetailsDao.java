package com.security.web.dao;


import com.security.web.entity.User;

/**
 * @description: demo user 查询
 * @author: sca
 * @create: 2019-07-27 14:57
 **/
public interface DemoUserDetailsDao {

    User loadUserDetailsById(String userId);

    User loadUserDetailsByTel(String userId);
}

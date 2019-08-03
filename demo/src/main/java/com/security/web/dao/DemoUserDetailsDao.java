package com.security.web.dao;


import com.security.web.entity.User;

/**
 * @description: demo user 查询
 * @author: sca
 * @create: 2019-07-27 14:57
 **/
public interface DemoUserDetailsDao {

    /**
     * 通过ID查询用户详情
     * @param userId
     * @return
     */
    User loadUserDetailsById(String userId);

    /**
     * 通过手机号查询用户详情
     * @param userId
     * @return
     */
    User loadUserDetailsByTel(String userId);
}

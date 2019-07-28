package com.security.web.dao.impl;

import com.security.web.dao.DemoUserDetailsDao;
import com.security.web.entity.User;
import com.security.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @description: demo user 查询实现层
 * @author: sca
 * @create: 2019-07-27 15:00
 **/
@Repository
public class DemoUserDetailsDaoImpl implements DemoUserDetailsDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserDetailsById(String userId) {
        return userMapper.loadUserDetailsById(userId);
    }

    @Override
    public User loadUserDetailsByTel(String userId) {

        return userMapper.loadUserDetailsByTel(userId);
    }
}

package com.security.web.mapper;

import com.security.web.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @description: demo userDetails mybatis映射文件
 * @author: sca
 * @create: 2019-07-27 15:02
 **/
public interface UserMapper {

    /**
     * 用户ID获取用户详情
     * @param userId
     * @return
     */
    @Select(value = "select * from user where user_name = #{userId}")
    @Results({@Result(column = "user_name",property = "userName"),
              @Result(column = "lock_status",property = "lockStatus"),
              @Result(column = "update_time",property = "updateTime"),
              @Result(column = "create_time",property = "createTime")})
    User loadUserDetailsById(String userId);

    /**
     * 手机号获取用户详情
     * @param tel
     * @return
     */
    @Select(value = "select * from user where tel = #{tel}")
    @Results({@Result(column = "user_name",property = "userName"),
            @Result(column = "lock_status",property = "lockStatus"),
            @Result(column = "update_time",property = "updateTime"),
            @Result(column = "create_time",property = "createTime")})
    User loadUserDetailsByTel(String tel);
}

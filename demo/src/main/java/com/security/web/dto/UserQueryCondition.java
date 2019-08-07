package com.security.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 查询用户条件基类
 * @author: sca
 * @create: 2019-07-19 14:50
 **/
@Data
public class UserQueryCondition {

    private String username;

    @ApiModelProperty(value = "查询用户年龄起始值")
    private int age;

    @ApiModelProperty(value = "查询用户年龄最大值")
    private int ageTo;
}

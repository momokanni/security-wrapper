package com.security.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 实体基类
 * @author: sca
 * @create: 2019-07-27 15:59
 **/
@Data
public class BaseEntity implements Serializable {

    private int id;

    private int updateTime;

    private int createTime;
}

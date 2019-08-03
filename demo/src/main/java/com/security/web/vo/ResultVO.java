package com.security.web.vo;

import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

/**
 * @description: 结果视图转换类
 * @author: sca
 * @create: 2019-07-27 21:09
 **/
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 返回码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
}

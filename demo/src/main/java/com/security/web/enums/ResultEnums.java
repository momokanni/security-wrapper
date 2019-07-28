package com.security.web.enums;

import lombok.Getter;

/**
 * @description: 响应结果枚举
 * @author: sca
 * @create: 2019-07-27 20:19
 **/
@Getter
public enum ResultEnums {

    /**
     * system errors
     */
    SUCCESS(200,"成功"),
    FAILED(201,"失败"),
    UNKONW(202,"未知错误"),
    PARAM_ERROR(203,"参数不正确"),
    PARAM_CONVERT_ERROR(204,"参数转换异常"),
    SYSTEM_ERROR(205,"系统错误"),
    /**
     * User exception
     */
    USER_NOT_EXISTS(401,"用户不存在"),
    USER_CREATE_ERROR(402,"用户创建失败"),
    ;

    private int code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

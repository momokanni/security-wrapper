package com.security.web.enums;

import lombok.Getter;

/**
 * @description: 账户的是否启用
 * @author: sca
 * @create: 2019-07-27 17:38
 **/
@Getter
public enum EnabledStatus {

    /**
     * 已启用
     */
    ENABLED("0",true),
    /**
     * 未启用
     */
    NOT_ENABLED("1",false),
    ;

    private String code;

    private boolean status;

    EnabledStatus(String code, boolean status) {
        this.code = code;
        this.status = status;
    }

    public static boolean getValue(String code){
        for (EnabledStatus status : values()){
            if (status.getCode().equals(code)){
                return status.isStatus();
            }
        }
        return false;
    }
}

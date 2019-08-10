package com.security.web.enums;

import lombok.Getter;

/**
 * @description: 账户锁定状态枚举
 * @author: sca
 * @create: 2019-07-27 17:35
 **/
@Getter
public enum LockStatus {

    /**
     * 未锁定
     */
    UNLOCKED("0",true),
    /**
     * 已锁定
     */
    LOCKED("1",false)
    ;
    private String code;

    private boolean status;

    LockStatus(String code, boolean status) {
        this.code = code;
        this.status = status;
    }

    public static boolean getValue(String code){
        for (LockStatus lockStatus : values()){
            if (lockStatus.getCode().equals(code)){
                return lockStatus.isStatus();
            }
        }
        return false;
    }
}

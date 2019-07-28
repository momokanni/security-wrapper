package com.security.core.properties;

import lombok.Data;

/**
 * @description: 社交配置基类
 * @author: sca
 * @create: 2019-07-23 15:20
 **/
@Data
public class BaseSocialProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;
}

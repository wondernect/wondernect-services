package com.wondernect.services.ums.user.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: RBACConfig
 * Author: chenxun
 * Date: 2020-09-20 10:09
 * Description:
 */
@Configuration
@EnableConfigurationProperties(UserConfigProperties.class)
public class UserConfig {
    /**
     * 该配置使用@EnableConfigurationProperties(UserConfigProperties.class)使UserConfigProperties配置生效
     */
}

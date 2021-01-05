package com.wondernect.services.ums.stars.config;

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
@EnableConfigurationProperties(UmsStarsConfigProperties.class)
public class UmsStarsConfig {
    /**
     * 该配置使用@EnableConfigurationProperties(UmsStarsConfigProperties.class)使UmsStarsConfigProperties配置生效
     */
}

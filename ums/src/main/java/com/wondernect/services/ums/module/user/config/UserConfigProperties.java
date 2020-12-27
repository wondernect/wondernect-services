package com.wondernect.services.ums.module.user.config;

import com.wondernect.elements.property.source.WondernectPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: RBACConfigProperties
 * Author: chenxun
 * Date: 2020-09-20 10:10
 * Description:
 */
@Component
@Primary
@PropertySource(value = {"classpath:application.properties", "classpath:application.yml", "classpath:application.yaml"}, ignoreResourceNotFound = true, factory = WondernectPropertySourceFactory.class)
@ConfigurationProperties(prefix = "wondernect.services.ums.user")
public class UserConfigProperties implements Serializable {

    private static final long serialVersionUID = 3260656889942519185L;

    private String roleTypeId = "UMS_USER_TYPE"; // UMS注册用户角色类型

    private String roleId = "UMS_USER"; // UMS注册用户角色

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}

package com.wondernect.services.ums.stars.config;

import com.wondernect.elements.property.source.WondernectPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2021, wondernect.com
 * FileName: UmsStarsConfigProperties
 * Author: chenxun
 * Date: 1/5/21 3:25 PM
 * Description:
 */
@Component
@Primary
@PropertySource(value = {"classpath:application.properties", "classpath:application.yml", "classpath:application.yaml"}, ignoreResourceNotFound = true, factory = WondernectPropertySourceFactory.class)
@ConfigurationProperties(prefix = "wondernect.stars")
public class UmsStarsConfigProperties {

    private String appId = "UMS"; // UMS应用id

    private String appSecret = "10001"; // UMS应用密钥

    private String userId = "10001"; // UMS管理员id

    private String username = "admin"; // UMS管理员登录名

    private String password = "d7c6c07a0a04ba4e65921e2f90726384"; // UMS管理员登录密码(123456两次md5)

    private String roleTypeId = "UMS_ADMIN_TYPE"; // UMS管理员角色类型

    private String roleId = "UMS_ADMIN"; // UMS管理员角色

    private String rootFilePathId = "ROOT_FILE_PATH"; // 文件存储根节点id

    private String rootMenuId = "ROOT_MENU"; // 根节点菜单id

    private String umsAdminRoleTypeId = "UMS_ADMIN_TYPE"; // 应用管理员角色类型id

    private String umsAdminRoleId = "UMS_ADMIN"; // 应用管理员角色id

    private String umsUserRoleTypeId = "UMS_USER_TYPE"; // 应用注册用户角色类型id

    private String umsUserRoleId = "UMS_USER"; // 应用注册用户角色id

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getRootFilePathId() {
        return rootFilePathId;
    }

    public void setRootFilePathId(String rootFilePathId) {
        this.rootFilePathId = rootFilePathId;
    }

    public String getRootMenuId() {
        return rootMenuId;
    }

    public void setRootMenuId(String rootMenuId) {
        this.rootMenuId = rootMenuId;
    }

    public String getUmsAdminRoleTypeId() {
        return umsAdminRoleTypeId;
    }

    public void setUmsAdminRoleTypeId(String umsAdminRoleTypeId) {
        this.umsAdminRoleTypeId = umsAdminRoleTypeId;
    }

    public String getUmsAdminRoleId() {
        return umsAdminRoleId;
    }

    public void setUmsAdminRoleId(String umsAdminRoleId) {
        this.umsAdminRoleId = umsAdminRoleId;
    }

    public String getUmsUserRoleTypeId() {
        return umsUserRoleTypeId;
    }

    public void setUmsUserRoleTypeId(String umsUserRoleTypeId) {
        this.umsUserRoleTypeId = umsUserRoleTypeId;
    }

    public String getUmsUserRoleId() {
        return umsUserRoleId;
    }

    public void setUmsUserRoleId(String umsUserRoleId) {
        this.umsUserRoleId = umsUserRoleId;
    }
}

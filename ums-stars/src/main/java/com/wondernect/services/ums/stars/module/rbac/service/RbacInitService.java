package com.wondernect.services.ums.stars.module.rbac.service;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.rbac.manager.MenuManager;
import com.wondernect.stars.rbac.manager.RoleManager;
import com.wondernect.stars.rbac.manager.RoleTypeManager;
import com.wondernect.stars.rbac.model.Menu;
import com.wondernect.stars.rbac.model.Role;
import com.wondernect.stars.rbac.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: InitService
 * Author: chenxun
 * Date: 2020-02-28 14:31
 * Description: 初始化服务
 */
@Service
public class RbacInitService implements ApplicationListener<WondernectBootEvent> {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private RoleTypeManager roleTypeManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private MenuManager menuManager;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                // 根节点菜单
                if (ESObjectUtils.isNull(menuManager.findById("-1"))) {
                    Menu menu = new Menu(
                            "根节点父菜单",
                            "-1",
                            "",
                            "根节点父菜单",
                            false,
                            false,
                            0,
                            ""
                    );
                    menu.setId("-1");
                    menuManager.save(menu);
                }
                if (ESObjectUtils.isNull(menuManager.findById(umsStarsConfigProperties.getRootMenuId()))) {
                    Menu menu = new Menu(
                            "根节点菜单",
                            umsStarsConfigProperties.getRootMenuId(),
                            "",
                            "根节点菜单",
                            false,
                            false,
                            0,
                            "-1"
                    );
                    menu.setId(umsStarsConfigProperties.getRootMenuId());
                    menuManager.save(menu);
                }
                // 设置初始化数据创建app和user
                wondernectCommonContext.getAuthorizeData().setAppId(umsStarsConfigProperties.getAppId());
                wondernectCommonContext.getAuthorizeData().setUserId(umsStarsConfigProperties.getUserId());
                // 管理员
                if (ESObjectUtils.isNull(roleTypeManager.findById(umsStarsConfigProperties.getUmsAdminRoleTypeId()))) {
                    RoleType roleType = new RoleType(
                            "UMS管理员角色类型",
                            "UMS管理员角色类型",
                            true,
                            false,
                            0
                    );
                    roleType.setId(umsStarsConfigProperties.getUmsAdminRoleTypeId());
                    roleTypeManager.save(roleType);
                }
                if (ESObjectUtils.isNull(roleManager.findById(umsStarsConfigProperties.getUmsAdminRoleId()))) {
                    Role role = new Role(
                            "UMS管理员角色",
                            "UMS管理员角色",
                            true,
                            false,
                            0,
                            umsStarsConfigProperties.getUmsAdminRoleTypeId()
                    );
                    role.setId(umsStarsConfigProperties.getUmsAdminRoleId());
                    roleManager.save(role);
                }
                // 注册用户
                if (ESObjectUtils.isNull(roleTypeManager.findById(umsStarsConfigProperties.getUmsUserRoleTypeId()))) {
                    RoleType roleType = new RoleType(
                            "UMS注册用户角色类型",
                            "UMS注册用户角色类型",
                            true,
                            false,
                            0
                    );
                    roleType.setId(umsStarsConfigProperties.getUmsUserRoleTypeId());
                    roleTypeManager.save(roleType);
                }
                if (ESObjectUtils.isNull(roleManager.findById(umsStarsConfigProperties.getUmsUserRoleId()))) {
                    Role role = new Role(
                            "UMS注册用户角色",
                            "UMS注册用户角色",
                            true,
                            false,
                            0,
                            umsStarsConfigProperties.getUmsUserRoleTypeId()
                    );
                    role.setId(umsStarsConfigProperties.getUmsUserRoleId());
                    roleManager.save(role);
                }
                break;
            }
            default:
                break;
        }
    }
}

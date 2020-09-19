package com.wondernect.services.ums.rbac;

import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
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
public class RBACInitService implements ApplicationListener<WondernectBootEvent> {

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
                // 初始化角色类型
                if (ESObjectUtils.isNull(roleTypeManager.findById("APP_ADMIN_TYPE"))) {
                    RoleType roleType = new RoleType(
                            "UMS应用管理员角色类型",
                            "UMS应用管理员角色类型",
                            true,
                            false,
                            0
                    );
                    roleType.setId("APP_ADMIN_TYPE");
                    roleTypeManager.save(roleType);
                }
                if (ESObjectUtils.isNull(roleTypeManager.findById("APP_USER_TYPE"))) {
                    RoleType roleType = new RoleType(
                            "UMS应用用户角色类型",
                            "UMS应用用户角色类型",
                            true,
                            false,
                            0
                    );
                    roleType.setId("APP_USER_TYPE");
                    roleTypeManager.save(roleType);
                }
                // 初始化角色
                if (ESObjectUtils.isNull(roleManager.findById("APP_ADMIN"))) {
                    Role role = new Role(
                            "UMS应用管理员角色",
                            "UMS应用管理员角色",
                            true,
                            false,
                            0,
                            "APP_ADMIN_TYPE"
                    );
                    role.setId("APP_ADMIN");
                    roleManager.save(role);
                }
                if (ESObjectUtils.isNull(roleManager.findById("APP_USER"))) {
                    Role role = new Role(
                            "UMS应用用户角色",
                            "UMS应用用户角色",
                            true,
                            false,
                            0,
                            "APP_USER_TYPE"
                    );
                    role.setId("APP_USER");
                    roleManager.save(role);
                }
                // 初始化根节点菜单
                if (ESObjectUtils.isNull(menuManager.findById("ROOT_MENU"))) {
                    Menu menu = new Menu(
                            "根节点菜单",
                            "ROOT_MENU",
                            "",
                            "根节点菜单",
                            true,
                            false,
                            0,
                            "-1"
                    );
                    menu.setId("ROOT_MENU");
                    menuManager.save(menu);
                }
                // 初始化UMS应用菜单节点
                if (ESObjectUtils.isNull(menuManager.findById("UMS_MENU"))) {
                    Menu menu = new Menu(
                            "UMS根节点菜单",
                            "UMS_MENU",
                            "",
                            "UMS根节点菜单",
                            true,
                            false,
                            0,
                            "ROOT_MENU"
                    );
                    menu.setId("UMS_MENU");
                    menu.setCreateApp("UMS");
                    menuManager.save(menu);
                }
                break;
            }
            default:
                break;
        }
    }
}

package com.wondernect.services.ums.user;

import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.user.em.Gender;
import com.wondernect.stars.user.em.UserType;
import com.wondernect.stars.user.manager.UserLocalAuthManager;
import com.wondernect.stars.user.manager.UserManager;
import com.wondernect.stars.user.model.User;
import com.wondernect.stars.user.model.UserLocalAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: DefaultUserOpenAPI
 * Author: chenxun
 * Date: 2019/4/8 11:32
 * Description:
 */
@Service
public class UserInitService implements ApplicationListener<WondernectBootEvent> {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserLocalAuthManager userAuthManager;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                // 初始化UMS应用管理员
                if (ESObjectUtils.isNull(userManager.findById("10001"))) {
                    User user = new User(
                            UserType.LOCAL,
                            "admin",
                            "UMS应用管理员",
                            Gender.UNKNOWN,
                            null,
                            "15860746397",
                            "sunbeamhome@163.com",
                            null,
                            null,
                            "APP_ADMIN_TYPE",
                            "APP_ADMIN",
                            true,
                            true,
                            false
                    );
                    user.setId("10001");
                    user.setCreateApp("UMS");
                    user = userManager.save(user);
                    userAuthManager.save(
                            new UserLocalAuth(
                                    user.getId(),
                                    "d7c6c07a0a04ba4e65921e2f90726384"
                            )
                    );
                }
                break;
            }
            default:
                break;
        }
    }
}

package com.wondernect.services.ums.stars.module.user.service;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
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
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserLocalAuthManager userAuthManager;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                wondernectCommonContext.getAuthorizeData().setAppId(umsStarsConfigProperties.getAppId());
                wondernectCommonContext.getAuthorizeData().setUserId(umsStarsConfigProperties.getUserId());
                // 初始化UMS应用管理员
                if (ESObjectUtils.isNull(userManager.findById(umsStarsConfigProperties.getUserId()))) {
                    User user = new User(
                            UserType.LOCAL,
                            umsStarsConfigProperties.getUsername(),
                            "UMS管理员",
                            Gender.UNKNOWN,
                            null,
                            "",
                            "",
                            null,
                            null,
                            umsStarsConfigProperties.getRoleTypeId(),
                            umsStarsConfigProperties.getRoleId(),
                            true,
                            true,
                            false
                    );
                    user.setId(umsStarsConfigProperties.getUserId());
                    userManager.save(user);
                    userAuthManager.save(
                            new UserLocalAuth(
                                    umsStarsConfigProperties.getUserId(),
                                    umsStarsConfigProperties.getPassword()
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

package com.wondernect.services.ums.stars.module.app.service;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.app.model.App;
import com.wondernect.stars.app.model.AppAuth;
import com.wondernect.stars.app.service.AppAuthService;
import com.wondernect.stars.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: AppInitService
 * Author: chenxun
 * Date: 2020-09-13 23:34
 * Description:
 */
@Service
public class AppInitService implements ApplicationListener<WondernectBootEvent> {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private AppService appService;

    @Autowired
    private AppAuthService appAuthService;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                wondernectCommonContext.getAuthorizeData().setAppId(umsStarsConfigProperties.getAppId());
                wondernectCommonContext.getAuthorizeData().setUserId(umsStarsConfigProperties.getUserId());
                // 初始化UMS应用
                if (ESObjectUtils.isNull(appService.findEntityById(umsStarsConfigProperties.getAppId()))) {
                    AppAuth appAuth = new AppAuth(umsStarsConfigProperties.getAppId(), umsStarsConfigProperties.getAppSecret(), umsStarsConfigProperties.getUserId(), 2, false);
                    appAuthService.saveEntity(appAuth);
                    App app = new App("UMS统一服务管理平台", "", "UMS统一服务管理平台", "", appAuth.getId());
                    app.setId(umsStarsConfigProperties.getAppId());
                    appService.saveEntity(app);
                }
                break;
            }
            default:
                break;
        }
    }
}

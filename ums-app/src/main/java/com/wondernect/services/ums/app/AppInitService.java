package com.wondernect.services.ums.app;

import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.app.model.App;
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
    private AppService appService;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                // 初始化UMS应用
                if (ESObjectUtils.isNull(appService.findEntityById("UMS"))) {
                    App app = new App(
                            "UMS统一服务管理平台",
                            "10001",
                            "",
                            "UMS统一服务管理平台",
                            "",
                            "10001"
                    );
                    app.setId("UMS");
                    appService.saveEntity(app);
                }
                break;
            }
            default:
                break;
        }
    }
}

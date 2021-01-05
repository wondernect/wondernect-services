package com.wondernect.services.ums.stars.module.app.service;

import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.app.dto.AppResponseDTO;
import com.wondernect.stars.app.model.App;
import com.wondernect.stars.app.service.AppService;
import com.wondernect.stars.user.model.User;
import com.wondernect.stars.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2020, wondernect.com
 * FileName: UserServerService
 * Author: chenxun
 * Date: 2020/12/29 17:52
 * Description:
 */
@Service
public class AppServerService extends AppService {

    @Autowired
    private UserService userService;

    @Override
    public AppResponseDTO generate(App app) {
        AppResponseDTO appResponseDTO = super.generate(app);
        User user = userService.findEntityById(appResponseDTO.getUserId());
        appResponseDTO.setUserName(ESObjectUtils.isNotNull(user) ? user.getName() : null);
        return appResponseDTO;
    }
}

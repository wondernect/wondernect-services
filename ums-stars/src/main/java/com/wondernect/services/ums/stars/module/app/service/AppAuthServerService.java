package com.wondernect.services.ums.stars.module.app.service;

import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.app.dto.auth.AppAuthResponseDTO;
import com.wondernect.stars.app.model.AppAuth;
import com.wondernect.stars.app.service.AppAuthService;
import com.wondernect.stars.user.model.User;
import com.wondernect.stars.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2020, wondernect.com
 * FileName: AppAuthServerService
 * Author: chenxun
 * Date: 2020/12/29 17:58
 * Description:
 */
@Service
public class AppAuthServerService extends AppAuthService {

    @Autowired
    private UserService userService;

    @Override
    public AppAuthResponseDTO generate(AppAuth appAuth) {
        AppAuthResponseDTO appAuthResponseDTO = super.generate(appAuth);
        User user = userService.findEntityById(appAuthResponseDTO.getUserId());
        appAuthResponseDTO.setUserName(ESObjectUtils.isNotNull(user) ? user.getName() : null);
        return appAuthResponseDTO;
    }
}

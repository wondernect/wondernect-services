package com.wondernect.services.ums.stars.context;

import com.wondernect.elements.authorize.context.impl.AbstractWondernectAuthorizeContext;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeAccessType;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.app.dto.auth.AppAuthResponseDTO;
import com.wondernect.stars.app.service.AppAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeContext extends AbstractWondernectAuthorizeContext {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private AppAuthService appAuthService;

    @Override
    public AuthorizeAccessType getAppAccessType(String appId, String userId) {
        if (umsStarsConfigProperties.getAppId().equals(appId)) {
            return AuthorizeAccessType.WRITE;
        }
        AppAuthResponseDTO appAuthResponseDTO = appAuthService.existByAppIdAndUserId(appId, userId);
        if (appAuthResponseDTO.getAccessType() == 1) {
            return AuthorizeAccessType.READ;
        } else if (appAuthResponseDTO.getAccessType() == 2) {
            return AuthorizeAccessType.WRITE;
        } else {
            return null;
        }
    }
}

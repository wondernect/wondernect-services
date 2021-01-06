package com.wondernect.services.ums.open.module.session.controller;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.authorize.context.config.WondernectServerContextConfigProperties;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.services.ums.open.module.session.dto.AuthorizeRequestDTO;
import com.wondernect.services.ums.open.module.session.dto.LoginResponseDTO;
import com.wondernect.stars.app.dto.AppResponseDTO;
import com.wondernect.stars.app.dto.AuthAppRequestDTO;
import com.wondernect.stars.app.feign.app.AppServerService;
import com.wondernect.stars.session.dto.code.CodeRequestDTO;
import com.wondernect.stars.session.dto.code.CodeResponseDTO;
import com.wondernect.stars.session.feign.codeSession.CodeSessionServerService;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.feign.user.UserServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: SessionController
 * Author: chenxun
 * Date: 2020-08-29 23:30
 * Description: 会话(登入登出)管理
 */
@Api(tags = "会话服务-登入、登出")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/open/session")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private WondernectServerContextConfigProperties wondernectServerContextConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private CodeSessionServerService codeSessionServerService;

    @Autowired
    private AppServerService appServerService;

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private HttpServletRequest request;

    @RequestLogger(module = "session", operation = "auth_login", description = "认证登录")
    @ApiOperation(value = "认证登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public BusinessData<LoginResponseDTO> auth(
            @ApiParam(value = "body请求参数", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody AuthorizeRequestDTO authorizeRequestDTO
    ) {
        // 设置请求头部requestId、appId、userId
        request.setAttribute(wondernectServerContextConfigProperties.getRequestPropertyName(), wondernectCommonContext.getRequestId());
        request.setAttribute(wondernectServerContextConfigProperties.getUserPropertyName(), authorizeRequestDTO.getUserId());
        AppResponseDTO appResponseDTO = appServerService.detail(authorizeRequestDTO.getAppId());
        if (ESObjectUtils.isNull(appResponseDTO)) {
            throw new BusinessException("应用不存在");
        }
        wondernectCommonContext.getAuthorizeData().setAppId(authorizeRequestDTO.getAppId());
        UserResponseDTO userResponseDTO = userServerService.detail(authorizeRequestDTO.getUserId());
        if (ESObjectUtils.isNull(userResponseDTO)) {
            throw new BusinessException("应用密钥绑定用户不存在");
        }
        if (!userResponseDTO.getEnable()) {
            throw new BusinessException("应用密钥绑定用户尚未激活");
        }
        wondernectCommonContext.getAuthorizeData().setUserId(authorizeRequestDTO.getUserId());
        // 认证应用密钥
        appServerService.auth(new AuthAppRequestDTO(appResponseDTO.getId(), authorizeRequestDTO.getAppSecret(), userResponseDTO.getId()));
        // 请求令牌
        CodeResponseDTO codeResponseDTO = codeSessionServerService.request(
                new CodeRequestDTO(
                        userResponseDTO.getId(),
                        "App密钥登录",
                        null,
                        wondernectCommonContext.getRequestIp(),
                        wondernectCommonContext.getDevicePlatform(),
                        wondernectCommonContext.getRequestDevice()
                )
        );
        return new BusinessData<>(new LoginResponseDTO(userResponseDTO, codeResponseDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "session", operation = "auth_logout", description = "认证登出")
    @ApiOperation(value = "认证登出", httpMethod = "POST")
    @PostMapping(value = "/logout")
    public BusinessData logout() {
        codeSessionServerService.deleteCache(wondernectCommonContext.getAuthorizeData().getToken());
        return new BusinessData(BusinessError.SUCCESS);
    }
}

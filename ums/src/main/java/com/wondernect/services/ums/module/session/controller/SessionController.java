package com.wondernect.services.ums.module.session.controller;

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
import com.wondernect.services.ums.module.session.dto.LoginRequestDTO;
import com.wondernect.services.ums.module.session.dto.LoginResponseDTO;
import com.wondernect.stars.session.dto.code.CodeRequestDTO;
import com.wondernect.stars.session.dto.code.CodeResponseDTO;
import com.wondernect.stars.session.feign.codeSession.CodeSessionServerService;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.dto.auth.local.AuthUserLocalAuthRequestDTO;
import com.wondernect.stars.user.feign.user.UserServerService;
import com.wondernect.stars.user.feign.userLocalAuth.UserLocalAuthServerService;
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
@RequestMapping(value = "/v1/ums/session")
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private WondernectServerContextConfigProperties wondernectServerContextConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private CodeSessionServerService codeSessionServerService;

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private UserLocalAuthServerService userLocalAuthServerService;

    @RequestLogger(module = "session", operation = "login", description = "登录")
    @ApiOperation(value = "登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public BusinessData<LoginResponseDTO> login(
            @ApiParam(value = "body请求参数", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody LoginRequestDTO loginRequestDTO,
            HttpServletRequest request
    ) {
        // 设置请求头部requestId
        request.setAttribute(wondernectServerContextConfigProperties.getRequestPropertyName(), wondernectCommonContext.getRequestId());
        // 设置请求头部userId，userId此时没有，设置request头部userId为username
        request.setAttribute(wondernectServerContextConfigProperties.getUserPropertyName(), loginRequestDTO.getUsername());
        UserResponseDTO userResponseDTO = userServerService.detailByUsername(loginRequestDTO.getUsername());
        if (ESObjectUtils.isNull(userResponseDTO)) {
            throw new BusinessException("用户不存在");
        }
        if (!userResponseDTO.getEnable()) {
            throw new BusinessException("用户尚未激活,不可登录");
        }
        wondernectCommonContext.getAuthorizeData().setUserId(userResponseDTO.getId());
        wondernectCommonContext.getAuthorizeData().setRole(userResponseDTO.getRoleId());
        // 设置请求头部userId，userId已拿到，正常设置request头部userId
        request.setAttribute(wondernectServerContextConfigProperties.getUserPropertyName(), wondernectCommonContext.getAuthorizeData().getUserId());
        userLocalAuthServerService.auth(userResponseDTO.getId(), new AuthUserLocalAuthRequestDTO(loginRequestDTO.getPassword()));
        CodeResponseDTO codeResponseDTO = codeSessionServerService.request(
                new CodeRequestDTO(
                        userResponseDTO.getId(),
                        "登录",
                        null,
                        wondernectCommonContext.getRequestIp(),
                        wondernectCommonContext.getDevicePlatform(),
                        wondernectCommonContext.getRequestDevice()
                )
        );
        return new BusinessData<>(new LoginResponseDTO(userResponseDTO, codeResponseDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "session", operation = "logout", description = "登出")
    @ApiOperation(value = "登出", httpMethod = "POST")
    @PostMapping(value = "/logout")
    public BusinessData logout() {
        codeSessionServerService.deleteCache(wondernectCommonContext.getAuthorizeData().getToken());
        return new BusinessData(BusinessError.SUCCESS);
    }
}

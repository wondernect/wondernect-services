package com.wondernect.services.ums.module.user.controller;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.authorize.context.config.WondernectServerContextConfigProperties;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.services.ums.module.user.config.UserConfigProperties;
import com.wondernect.stars.user.dto.SaveLocalUserRequestDTO;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.feign.user.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户
 */
@Api(tags = "用户服务-用户")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserConfigProperties userConfigProperties;

    @Autowired
    private WondernectServerContextConfigProperties wondernectServerContextConfigProperties;

    @Autowired
    private WondernectCommonContext wondernectCommonContext;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private HttpServletRequest request;

    @RequestLogger(module = "user", operation = "regist", description = "自主注册")
    @ApiOperation(value = "自主注册", httpMethod = "POST")
    @PostMapping(value = "/regist")
    public BusinessData<UserResponseDTO> regist(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveLocalUserRequestDTO saveUserRequestDTO
    ) {
        // 设置请求头部requestId、userId（userId此时没有，设置request头部userId为username）
        request.setAttribute(wondernectServerContextConfigProperties.getRequestPropertyName(), wondernectCommonContext.getRequestId());
        request.setAttribute(wondernectServerContextConfigProperties.getUserPropertyName(), saveUserRequestDTO.getUsername());
        saveUserRequestDTO.setRoleTypeId(userConfigProperties.getRoleTypeId());
        saveUserRequestDTO.setRoleId(userConfigProperties.getRoleId());
        return userFeignClient.create(saveUserRequestDTO);
    }
}

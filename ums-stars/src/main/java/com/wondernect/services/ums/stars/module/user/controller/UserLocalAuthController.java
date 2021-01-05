package com.wondernect.services.ums.stars.module.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.stars.user.dto.auth.local.SaveUserLocalAuthRequestDTO;
import com.wondernect.stars.user.dto.auth.local.UserLocalAuthResponseDTO;
import com.wondernect.stars.user.service.localauth.UserLocalAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户
 */
@Api(tags = "用户服务-用户本地认证", description = "用户本地认证")
@Validated
@RestController
@RequestMapping(value = "/v1/wondernect/user/local_auth")
public class UserLocalAuthController {

    @Autowired
    private UserLocalAuthService userLocalAuthService;

    @AuthorizeServer
    @RequestLogger(module = "local_auth", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/{id}/create")
    public BusinessData<UserLocalAuthResponseDTO> create(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserLocalAuthRequestDTO saveUserLocalAuthRequestDTO
    ) {
        return new BusinessData<>(userLocalAuthService.create(userId, saveUserLocalAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_auth", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<UserLocalAuthResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserLocalAuthRequestDTO saveUserLocalAuthRequestDTO
    ) {
        return new BusinessData<>(userLocalAuthService.update(userId, saveUserLocalAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_auth", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        userLocalAuthService.deleteById(userId);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "local_auth", operation = "detail", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<UserLocalAuthResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        return new BusinessData<>(userLocalAuthService.findById(userId));
    }
}

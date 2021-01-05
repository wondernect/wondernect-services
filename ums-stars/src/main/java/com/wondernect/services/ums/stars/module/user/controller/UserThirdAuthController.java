package com.wondernect.services.ums.stars.module.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.stars.user.dto.auth.third.SaveUserThirdAuthRequestDTO;
import com.wondernect.stars.user.dto.auth.third.UserThirdAuthResponseDTO;
import com.wondernect.stars.user.em.AppType;
import com.wondernect.stars.user.service.thirdauth.UserThirdAuthService;
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
 * FileName: UserThirdAuthController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户第三方认证
 */
@Api(tags = "用户服务-用户第三方认证", description = "用户第三方认证")
@Validated
@RestController
@RequestMapping(value = "/v1/wondernect/user/third_auth")
public class UserThirdAuthController {

    @Autowired
    private UserThirdAuthService userThirdAuthService;

    @AuthorizeServer
    @RequestLogger(module = "third_auth", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/{id}/create")
    public BusinessData<UserThirdAuthResponseDTO> create(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserThirdAuthRequestDTO saveUserThirdAuthRequestDTO
    ) {
        return new BusinessData<>(userThirdAuthService.create(userId, saveUserThirdAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "third_auth", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<UserThirdAuthResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveUserThirdAuthRequestDTO saveUserThirdAuthRequestDTO
    ) {
        return new BusinessData<>(userThirdAuthService.update(userId, saveUserThirdAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "third_auth", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType
    ) {
        userThirdAuthService.deleteByUserIdAndAppType(userId, appType);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "third_auth", operation = "detail", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<UserThirdAuthResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType
    ) {
        return new BusinessData<>(userThirdAuthService.findByUserIdAndAppType(userId, appType));
    }

    @AuthorizeServer
    @RequestLogger(module = "third_auth", operation = "detailByAppTypeAndAppUserId", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<UserThirdAuthResponseDTO> detailByAppTypeAndAppUserId(
            @ApiParam(required = true) @NotNull(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType,
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @RequestParam(value = "app_user_id", required = false) String appUserId
    ) {
        return new BusinessData<>(userThirdAuthService.findByAppTypeAndAppUserId(appType, appUserId));
    }
}

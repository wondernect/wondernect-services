package com.wondernect.services.ums.open.module.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.stars.user.dto.auth.third.UserThirdAuthResponseDTO;
import com.wondernect.stars.user.em.AppType;
import com.wondernect.stars.user.feign.userThirdAuth.UserThirdAuthFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserThirdAuthController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户第三方认证
 */
@Api(tags = "用户服务-用户第三方认证")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/open/user/third_auth")
public class UserThirdAuthController {

    @Autowired
    private UserThirdAuthFeignClient userThirdAuthFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "third_auth", operation = "detail", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<UserThirdAuthResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotBlank(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType
    ) {
        return userThirdAuthFeignClient.detail(userId, appType);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "third_auth", operation = "detailByAppTypeAndAppUserId", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<UserThirdAuthResponseDTO> detailByAppTypeAndAppUserId(
            @ApiParam(required = true) @NotBlank(message = "app类型不能为空") @RequestParam(value = "app_type", required = false) AppType appType,
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @RequestParam(value = "app_user_id", required = false) String appUserId
    ) {
        return userThirdAuthFeignClient.detailByAppTypeAndAppUserId(appType, appUserId);
    }
}

package com.wondernect.services.ums.open.module.session.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.session.dto.captcha.*;
import com.wondernect.stars.session.feign.captchaSession.CaptchaSessionFeignClient;
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
 * FileName: CaptchaSessionController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 验证码会话
 */
@Api(tags = "会话服务-验证码会话")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/session/captcha")
public class CaptchaSessionController {

    @Autowired
    private CaptchaSessionFeignClient captchaSessionFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "captcha-session", operation = "request", description = "请求")
    @ApiOperation(value = "请求", httpMethod = "POST")
    @PostMapping(value = "/request")
    public BusinessData<CaptchaResponseDTO> request(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody CaptchaRequestDTO captchaRequestDTO
    ) {
        return captchaSessionFeignClient.request(captchaRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "captcha-session", operation = "deleteCache", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/cache_delete")
    public BusinessData deleteCache(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        return captchaSessionFeignClient.deleteCache(captchaSessionId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "captcha-session", operation = "detailCache", description = "获取详情", recordResponse = false)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/cache_detail")
    public BusinessData<CaptchaResponseDTO> detailCache(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        return captchaSessionFeignClient.detailCache(captchaSessionId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "captcha-session", operation = "authCache", description = "验证", recordResponse = false)
    @ApiOperation(value = "验证", httpMethod = "POST")
    @PostMapping(value = "/cache_auth")
    public BusinessData<CaptchaResponseDTO> authCache(
            @ApiParam(required = true) @NotNull(message = "验证请求参数不能为空") @Validated @RequestBody CaptchaAuthRequestDTO captchaAuthRequestDTO
    ) {
        return captchaSessionFeignClient.authCache(captchaAuthRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "captcha-session", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<CaptchaResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageCaptchaRequestDTO pageCaptchaRequestDTO
    ) {
        return captchaSessionFeignClient.page(pageCaptchaRequestDTO);
    }
}

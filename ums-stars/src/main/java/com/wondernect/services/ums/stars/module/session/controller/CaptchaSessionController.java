package com.wondernect.services.ums.stars.module.session.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.session.dto.captcha.*;
import com.wondernect.stars.session.service.captcha.CaptchaSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: CaptchaSessionController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 验证码会话
 */
@Api(tags = "会话服务-验证码会话", description = "验证码会话")
@Validated
@RestController
@RequestMapping(value = "/v1/wondernect/session/captcha")
public class CaptchaSessionController {

    @Autowired
    private CaptchaSessionService captchaSessionService;

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "request", description = "请求(缓存&数据库)")
    @ApiOperation(value = "请求(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/request")
    public BusinessData<CaptchaResponseDTO> request(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody CaptchaRequestDTO captchaRequestDTO
    ) {
        return new BusinessData<>(captchaSessionService.requestCaptchaSession(captchaRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "delete", description = "删除(缓存&数据库)")
    @ApiOperation(value = "删除(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        captchaSessionService.deleteById(captchaSessionId);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "detail", description = "获取详情(缓存&数据库)", recordResponse = false)
    @ApiOperation(value = "获取详情(缓存&数据库)", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<CaptchaResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        return new BusinessData<>(captchaSessionService.findById(captchaSessionId));
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "deleteCache", description = "删除(缓存&数据库)")
    @ApiOperation(value = "删除(缓存)", httpMethod = "POST")
    @PostMapping(value = "/{id}/cache_delete")
    public BusinessData deleteCache(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        captchaSessionService.deleteCacheById(captchaSessionId);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "detailCache", description = "获取详情(缓存)", recordResponse = false)
    @ApiOperation(value = "获取详情(缓存)", httpMethod = "GET")
    @GetMapping(value = "/{id}/cache_detail")
    public BusinessData<CaptchaResponseDTO> detailCache(
            @ApiParam(required = true) @NotBlank(message = "验证码id不能为空") @PathVariable(value = "id", required = false) String captchaSessionId
    ) {
        return new BusinessData<>(captchaSessionService.findCacheById(captchaSessionId));
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "authCache", description = "验证(缓存)", recordResponse = false)
    @ApiOperation(value = "验证(缓存)", httpMethod = "POST")
    @PostMapping(value = "/cache_auth")
    public BusinessData<CaptchaResponseDTO> authCache(
            @ApiParam(required = true) @NotNull(message = "验证请求参数不能为空") @Validated @RequestBody CaptchaAuthRequestDTO captchaAuthRequestDTO
    ) {
        return new BusinessData<>(captchaSessionService.authCacheCaptchaSession(captchaAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "list", description = "列表(数据库)", recordResponse = false)
    @ApiOperation(value = "列表(数据库)", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<CaptchaResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody ListCaptchaRequestDTO listCaptchaRequestDTO
    ) {
        return new BusinessData<>(captchaSessionService.list(listCaptchaRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "captcha-session", operation = "page", description = "分页(数据库)", recordResponse = false)
    @ApiOperation(value = "分页(数据库)", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<CaptchaResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageCaptchaRequestDTO pageCaptchaRequestDTO
    ) {
        return new BusinessData<>(captchaSessionService.page(pageCaptchaRequestDTO));
    }
}

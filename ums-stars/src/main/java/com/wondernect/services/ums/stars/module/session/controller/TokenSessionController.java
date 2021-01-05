package com.wondernect.services.ums.stars.module.session.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.session.dto.token.*;
import com.wondernect.stars.session.service.token.TokenSessionService;
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
 * FileName: CodeSessionController
 * Author: chenxun
 * Date: 2019/6/5 14:44
 * Description: 令牌会话
 */
@Api(tags = "会话服务-永久会话", description = "永久会话")
@Validated
@RestController
@RequestMapping(value = "/v1/wondernect/session/token")
public class TokenSessionController {

    @Autowired
    private TokenSessionService tokenSessionService;

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "request", description = "请求令牌(缓存&数据库)")
    @ApiOperation(value = "请求令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/request")
    public BusinessData<TokenResponseDTO> request(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody TokenRequestDTO tokenRequestDTO
    ) {
        return new BusinessData<>(tokenSessionService.requestTokenSession(tokenRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "delete", description = "删除令牌(缓存&数据库)")
    @ApiOperation(value = "删除令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/{token}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "令牌不能为空") @PathVariable(value = "token", required = false) String token
    ) {
        tokenSessionService.deleteByToken(token);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "detail", description = "获取令牌(缓存&数据库)", recordResponse = false)
    @ApiOperation(value = "获取令牌(缓存&数据库)", httpMethod = "GET")
    @GetMapping(value = "/{token}/detail")
    public BusinessData<TokenResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "令牌不能为空") @PathVariable(value = "token", required = false) String token
    ) {
        return new BusinessData<>(tokenSessionService.findByToken(token));
    }

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "refresh", description = "刷新令牌(缓存&数据库)")
    @ApiOperation(value = "刷新令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/refresh")
    public BusinessData<TokenResponseDTO> refresh(
            @ApiParam(required = true) @NotNull(message = "刷新请求参数不能为空") @Validated @RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO
    ) {
        return new BusinessData<>(tokenSessionService.refreshTokenSession(tokenRefreshRequestDTO));
    }

    @RequestLogger(module = "token-session", operation = "auth", description = "验证令牌(缓存&数据库)", recordResponse = false)
    @ApiOperation(value = "验证令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/auth")
    public BusinessData<TokenResponseDTO> auth(
            @ApiParam(required = true) @NotNull(message = "验证请求参数不能为空") @Validated @RequestBody TokenAuthRequestDTO tokenAuthRequestDTO
    ) {
        return new BusinessData<>(tokenSessionService.authTokenSession(tokenAuthRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "list", description = "列表(数据库)", recordResponse = false)
    @ApiOperation(value = "列表(数据库)", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<TokenResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody ListTokenRequestDTO listTokenRequestDTO
    ) {
        return new BusinessData<>(tokenSessionService.list(listTokenRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "token-session", operation = "page", description = "分页(数据库)", recordResponse = false)
    @ApiOperation(value = "分页(数据库)", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<TokenResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageTokenRequestDTO pageTokenRequestDTO
    ) {
        return new BusinessData<>(tokenSessionService.page(pageTokenRequestDTO));
    }
}

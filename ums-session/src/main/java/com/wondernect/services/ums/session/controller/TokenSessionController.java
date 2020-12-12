package com.wondernect.services.ums.session.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.session.dto.token.*;
import com.wondernect.stars.session.feign.tokenSession.TokenSessionFeignClient;
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
 * FileName: CodeSessionController
 * Author: chenxun
 * Date: 2019/6/5 14:44
 * Description: 令牌会话
 */
@Api(tags = "永久会话", description = "永久会话")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/session/token")
public class TokenSessionController {

    @Autowired
    private TokenSessionFeignClient tokenSessionFeignClient;

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "请求令牌(缓存&数据库)", httpMethod = "POST")
    // @PostMapping(value = "/request")
    // public BusinessData<TokenResponseDTO> request(
    //         @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody TokenRequestDTO tokenRequestDTO
    // ) {
    //     return tokenSessionFeignClient.request(tokenRequestDTO);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "token-session", operation = "delete", description = "删除令牌(缓存&数据库)")
    @ApiOperation(value = "删除令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/{token}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "令牌不能为空") @PathVariable(value = "token", required = false) String token
    ) {
        return tokenSessionFeignClient.delete(token);
    }

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "获取令牌(缓存&数据库)", httpMethod = "GET")
    // @GetMapping(value = "/{token}/detail")
    // public BusinessData<TokenResponseDTO> detail(
    //         @ApiParam(required = true) @NotBlank(message = "令牌不能为空") @PathVariable(value = "token", required = false) String token
    // ) {
    //     return tokenSessionFeignClient.get(token);
    // }
    //
    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "刷新令牌(缓存&数据库)", httpMethod = "POST")
    // @PostMapping(value = "/refresh")
    // public BusinessData<TokenResponseDTO> refresh(
    //         @ApiParam(required = true) @NotNull(message = "刷新请求参数不能为空") @Validated @RequestBody TokenRefreshRequestDTO tokenRefreshRequestDTO
    // ) {
    //     return tokenSessionFeignClient.refresh(tokenRefreshRequestDTO);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "token-session", operation = "auth", description = "验证令牌(缓存&数据库)", recordResponse = false)
    @ApiOperation(value = "验证令牌(缓存&数据库)", httpMethod = "POST")
    @PostMapping(value = "/auth")
    public BusinessData<TokenResponseDTO> auth(
            @ApiParam(required = true) @NotNull(message = "验证请求参数不能为空") @Validated @RequestBody TokenAuthRequestDTO tokenAuthRequestDTO
    ) {
        return tokenSessionFeignClient.auth(tokenAuthRequestDTO);
    }

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "列表(数据库)", httpMethod = "POST")
    // @PostMapping(value = "/list")
    // public BusinessData<List<TokenResponseDTO>> list(
    //         @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody ListTokenRequestDTO listTokenRequestDTO
    // ) {
    //     return tokenSessionFeignClient.list(listTokenRequestDTO);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "token-session", operation = "page", description = "分页(数据库)", recordResponse = false)
    @ApiOperation(value = "分页(数据库)", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<TokenResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageTokenRequestDTO pageTokenRequestDTO
    ) {
        return tokenSessionFeignClient.page(pageTokenRequestDTO);
    }
}

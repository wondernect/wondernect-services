package com.wondernect.services.ums.session.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.session.dto.code.*;
import com.wondernect.stars.session.feign.codeSession.CodeSessionFeignClient;
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
@Api(tags = "临时会话", description = "临时会话")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/session/code")
public class CodeSessionController {

    @Autowired
    private CodeSessionFeignClient codeSessionFeignClient;

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "请求(缓存&数据库)", httpMethod = "POST")
    // @PostMapping(value = "/request")
    // public BusinessData<CodeResponseDTO> request(
    //         @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody CodeRequestDTO codeRequestDTO
    // ) {
    //     return codeSessionFeignClient.request(codeRequestDTO);
    // }
    //
    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "删除(缓存&数据库)", httpMethod = "POST")
    // @PostMapping(value = "/{code}/delete")
    // public BusinessData delete(
    //         @ApiParam(required = true) @NotBlank(message = "令牌code不能为空") @PathVariable(value = "code", required = false) String code
    // ) {
    //     return codeSessionFeignClient.delete(code);
    // }
    //
    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "获取(缓存&数据库)", httpMethod = "GET")
    // @GetMapping(value = "/{code}/detail")
    // public BusinessData<CodeResponseDTO> detail(
    //         @ApiParam(required = true) @NotBlank(message = "令牌code不能为空") @PathVariable(value = "code", required = false) String code
    // ) {
    //     return codeSessionFeignClient.detail(code);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "code-session", operation = "deleteCache", description = "删除(缓存)")
    @ApiOperation(value = "删除(缓存)", httpMethod = "POST")
    @PostMapping(value = "/{code}/cache_delete")
    public BusinessData deleteCache(
            @ApiParam(required = true) @NotBlank(message = "令牌code不能为空") @PathVariable(value = "code", required = false) String code
    ) {
        return codeSessionFeignClient.deleteCache(code);
    }

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "获取(缓存)", httpMethod = "GET")
    // @GetMapping(value = "/{code}/cache_detail")
    // public BusinessData<CodeResponseDTO> detailCache(
    //         @ApiParam(required = true) @NotBlank(message = "令牌code不能为空") @PathVariable(value = "code", required = false) String code
    // ) {
    //     return codeSessionFeignClient.detailCache(code);
    // }
    //
    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "续约/刷新(缓存&数据库)", httpMethod = "POST")
    // @PostMapping(value = "/refresh")
    // public BusinessData<CodeResponseDTO> refresh(
    //         @ApiParam(required = true) @NotNull(message = "刷新请求参数不能为空") @Validated @RequestBody CodeRefreshRequestDTO codeRefreshRequestDTO
    // ) {
    //     return codeSessionFeignClient.refresh(codeRefreshRequestDTO);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "code-session", operation = "authCache", description = "验证(缓存)")
    @ApiOperation(value = "验证(缓存)", httpMethod = "POST")
    @PostMapping(value = "/cache_auth")
    public BusinessData<CodeResponseDTO> authCache(
            @ApiParam(required = true) @NotNull(message = "验证请求参数不能为空") @Validated @RequestBody CodeAuthRequestDTO codeAuthRequestDTO
    ) {
        return codeSessionFeignClient.authCache(codeAuthRequestDTO);
    }

    // @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    // @ApiOperation(value = "列表(数据库)", httpMethod = "POST")
    // @PostMapping(value = "/list")
    // public BusinessData<List<CodeResponseDTO>> list(
    //         @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody ListCodeRequestDTO listCodeRequestDTO
    // ) {
    //     return codeSessionFeignClient.list(listCodeRequestDTO);
    // }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "code-session", operation = "page", description = "分页(数据库)")
    @ApiOperation(value = "分页(数据库)", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<CodeResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageCodeRequestDTO pageCodeRequestDTO
    ) {
        return codeSessionFeignClient.page(pageCodeRequestDTO);
    }
}

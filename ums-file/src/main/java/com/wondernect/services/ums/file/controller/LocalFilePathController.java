package com.wondernect.services.ums.file.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.file.dto.*;
import com.wondernect.stars.file.feign.path.LocalFilePathFeignClient;
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
 * Copyright (C), 2020, wondernect.com
 * FileName: RoleTypeController
 * Author: chenxun
 * Date: 2020-02-21 14:07
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/ums/file/path")
@Validated
@Api(tags = "本地文件存储路径", description = "本地文件存储路径")
public class LocalFilePathController {

    @Autowired
    private LocalFilePathFeignClient localFilePathFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<LocalFilePathResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveLocalFilePathRequestDTO saveLocalFilePathRequestDTO
    ) {
        return localFilePathFeignClient.create(saveLocalFilePathRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<LocalFilePathResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveLocalFilePathRequestDTO saveLocalFilePathRequestDTO
    ) {
        return localFilePathFeignClient.update(id, saveLocalFilePathRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return localFilePathFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "detail", description = "获取")
    @ApiOperation(value = "获取", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<LocalFilePathResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return localFilePathFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "list", description = "列表")
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<LocalFilePathResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListLocalFilePathRequestDTO listLocalFilePathRequestDTO
    ) {
        return localFilePathFeignClient.list(listLocalFilePathRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "page", description = "分页")
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<LocalFilePathResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageLocalFilePathRequestDTO pageLocalFilePathRequestDTO
    ) {
        return localFilePathFeignClient.page(pageLocalFilePathRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "root", description = "获取根节点")
    @ApiOperation(value = "获取根节点", httpMethod = "GET")
    @GetMapping(value = "/root")
    public BusinessData<LocalFilePathResponseDTO> root() {
        return localFilePathFeignClient.root();
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local_file_path", operation = "tree", description = "树形结构")
    @ApiOperation(value = "树形结构", httpMethod = "GET")
    @GetMapping(value = "/{root_file_path_id}/tree")
    public BusinessData<LocalFilePathTreeResponseDTO> tree(
            @ApiParam(required = true) @NotBlank(message = "根节点文件路径不能为空") @PathVariable(value = "root_file_path_id", required = false) String rootFilePathId
    ) {
        return localFilePathFeignClient.tree(rootFilePathId);
    }
}

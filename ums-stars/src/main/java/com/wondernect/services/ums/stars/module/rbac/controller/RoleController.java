package com.wondernect.services.ums.stars.module.rbac.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.rbac.dto.role.ListRoleRequestDTO;
import com.wondernect.stars.rbac.dto.role.PageRoleRequestDTO;
import com.wondernect.stars.rbac.dto.role.RoleResponseDTO;
import com.wondernect.stars.rbac.dto.role.SaveRoleRequestDTO;
import com.wondernect.stars.rbac.service.role.RoleService;
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
 * FileName: RoleController
 * Author: chenxun
 * Date: 2020-02-21 14:07
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/wondernect/rbac/role")
@Validated
@Api(tags = "权限服务-角色")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "create", description = "创建角色")
    @ApiOperation(value = "创建角色", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<RoleResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRoleRequestDTO saveRoleRequestDTO
    ) {
        return new BusinessData<>(roleService.create(saveRoleRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "update", description = "更新角色")
    @ApiOperation(value = "更新角色", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<RoleResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRoleRequestDTO saveRoleRequestDTO
    ) {
        return new BusinessData<>(roleService.update(id, saveRoleRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "delete", description = "删除角色")
    @ApiOperation(value = "删除角色", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        roleService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "detail", description = "获取角色详情", recordResponse = false)
    @ApiOperation(value = "获取角色详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<RoleResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(roleService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "list", description = "角色列表", recordResponse = false)
    @ApiOperation(value = "角色列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<RoleResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListRoleRequestDTO listRoleRequestDTO
    ) {
        return new BusinessData<>(roleService.list(listRoleRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "role", operation = "page", description = "角色分页", recordResponse = false)
    @ApiOperation(value = "角色分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<RoleResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageRoleRequestDTO pageRoleRequestDTO
    ) {
        return new BusinessData<>(roleService.page(pageRoleRequestDTO));
    }
}

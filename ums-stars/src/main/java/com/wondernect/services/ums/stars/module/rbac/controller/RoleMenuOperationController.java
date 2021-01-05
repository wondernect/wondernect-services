package com.wondernect.services.ums.stars.module.rbac.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.rbac.dto.rolemenuoperation.ListRoleMenuOperationRequestDTO;
import com.wondernect.stars.rbac.dto.rolemenuoperation.PageRoleMenuOperationRequestDTO;
import com.wondernect.stars.rbac.dto.rolemenuoperation.RoleMenuOperationRequestDTO;
import com.wondernect.stars.rbac.dto.rolemenuoperation.RoleMenuOperationResponseDTO;
import com.wondernect.stars.rbac.service.rolemenuoperation.RoleMenuOperationService;
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
 * FileName: RoleMenuOperationController
 * Author: chenxun
 * Date: 2020-02-21 14:08
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/wondernect/rbac/role_menu_operation")
@Validated
@Api(tags = "权限服务-角色-菜单-操作")
public class RoleMenuOperationController {

    @Autowired
    private RoleMenuOperationService roleMenuOperationService;

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "add", description = "勾选操作")
    @ApiOperation(value = "勾选操作", httpMethod = "POST")
    @PostMapping(value = "/add")
    public BusinessData add(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuOperationRequestDTO roleMenuOperationRequestDTO
    ) {
        roleMenuOperationService.add(roleMenuOperationRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "edit", description = "编辑勾选操作")
    @ApiOperation(value = "编辑勾选操作", httpMethod = "POST")
    @PostMapping(value = "/edit")
    public BusinessData edit(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuOperationRequestDTO roleMenuOperationRequestDTO
    ) {
        roleMenuOperationService.edit(roleMenuOperationRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "delete", description = "取消勾选操作")
    @ApiOperation(value = "取消勾选操作", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuOperationRequestDTO roleMenuOperationRequestDTO
    ) {
        roleMenuOperationService.delete(roleMenuOperationRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "detail", description = "获取角色菜单对应操作的相关信息", recordResponse = false)
    @ApiOperation(value = "获取角色菜单对应操作的相关信息", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<RoleMenuOperationResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "角色不能为空") @RequestParam(value = "role_id", required = false) String roleId,
            @ApiParam(required = true) @NotBlank(message = "菜单不能为空") @RequestParam(value = "menu_id", required = false) String menuId,
            @ApiParam(required = true) @NotBlank(message = "操作不能为空") @RequestParam(value = "operation_id", required = false) String operationId
    ) {
        return new BusinessData<>(roleMenuOperationService.findByRoleIdAndMenuIdAndOperationId(roleId, menuId, operationId));
    };

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "list", description = "角色权限菜单操作列表", recordResponse = false)
    @ApiOperation(value = "角色权限菜单操作列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<RoleMenuOperationResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListRoleMenuOperationRequestDTO listRoleMenuOperationRequestDTO
    ) {
        return new BusinessData<>(roleMenuOperationService.list(listRoleMenuOperationRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "role_menu_operation", operation = "page", description = "角色权限菜单操作分页", recordResponse = false)
    @ApiOperation(value = "角色权限菜单操作分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<RoleMenuOperationResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageRoleMenuOperationRequestDTO pageRoleMenuOperationRequestDTO
    ) {
        return new BusinessData<>(roleMenuOperationService.page(pageRoleMenuOperationRequestDTO));
    }
}

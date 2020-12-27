package com.wondernect.services.ums.module.rbac.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.rbac.dto.roletype.ListRoleTypeRequestDTO;
import com.wondernect.stars.rbac.dto.roletype.PageRoleTypeRequestDTO;
import com.wondernect.stars.rbac.dto.roletype.RoleTypeResponseDTO;
import com.wondernect.stars.rbac.dto.roletype.SaveRoleTypeRequestDTO;
import com.wondernect.stars.rbac.feign.roleType.RoleTypeFeignClient;
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
@RequestMapping(value = "/v1/ums/rbac/role_type")
@Validated
@Api(tags = "权限管理服务-角色类型")
public class RoleTypeController {

    @Autowired
    private RoleTypeFeignClient roleTypeFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "create", description = "创建角色类型")
    @ApiOperation(value = "创建角色类型", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<RoleTypeResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRoleTypeRequestDTO saveRoleTypeRequestDTO
    ) {
        return roleTypeFeignClient.create(saveRoleTypeRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "update", description = "更新角色类型")
    @ApiOperation(value = "更新角色类型", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<RoleTypeResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRoleTypeRequestDTO saveRoleTypeRequestDTO
    ) {
        return roleTypeFeignClient.update(id, saveRoleTypeRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "delete", description = "删除角色类型")
    @ApiOperation(value = "删除角色类型", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return roleTypeFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "detail", description = "获取角色类型", recordResponse = false)
    @ApiOperation(value = "获取角色类型", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<RoleTypeResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return roleTypeFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "list", description = "角色类型列表", recordResponse = false)
    @ApiOperation(value = "角色类型列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<RoleTypeResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListRoleTypeRequestDTO listRoleTypeRequestDTO
    ) {
        return roleTypeFeignClient.list(listRoleTypeRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_type", operation = "page", description = "角色类型分页", recordResponse = false)
    @ApiOperation(value = "角色类型分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<RoleTypeResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageRoleTypeRequestDTO pageRoleTypeRequestDTO
    ) {
        return roleTypeFeignClient.page(pageRoleTypeRequestDTO);
    }
}

package com.wondernect.services.ums.module.rbac.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.stars.rbac.dto.MenuAuthorityResponseDTO;
import com.wondernect.stars.rbac.dto.RoleAuthorityResponseDTO;
import com.wondernect.stars.rbac.dto.menu.MenuResponseDTO;
import com.wondernect.stars.rbac.dto.rolemenu.RoleMenuRequestDTO;
import com.wondernect.stars.rbac.dto.rolemenu.RoleMenuResponseDTO;
import com.wondernect.stars.rbac.dto.rolemenu.RoleMenuTreeResponseDTO;
import com.wondernect.stars.rbac.feign.menu.MenuServerService;
import com.wondernect.stars.rbac.feign.roleMenu.RoleMenuFeignClient;
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
 * FileName: RoleMenuController
 * Author: chenxun
 * Date: 2020-02-21 14:08
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/ums/rbac/role_menu")
@Validated
@Api(tags = "权限管理服务-角色菜单关系")
public class RoleMenuController {

    @Autowired
    private RoleMenuFeignClient roleMenuFeignClient;

    @Autowired
    private MenuServerService menuServerService;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "add", description = "勾选菜单")
    @ApiOperation(value = "勾选菜单", httpMethod = "POST")
    @PostMapping(value = "/add")
    public BusinessData add(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuRequestDTO roleMenuRequestDTO
    ) {
        return roleMenuFeignClient.add(roleMenuRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "edit", description = "编辑勾选菜单")
    @ApiOperation(value = "编辑勾选菜单", httpMethod = "POST")
    @PostMapping(value = "/edit")
    public BusinessData edit(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuRequestDTO roleMenuRequestDTO
    ) {
        return roleMenuFeignClient.edit(roleMenuRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "delete", description = "取消勾选菜单")
    @ApiOperation(value = "取消勾选菜单", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) RoleMenuRequestDTO roleMenuRequestDTO
    ) {
        return roleMenuFeignClient.delete(roleMenuRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "detail", description = "获取角色菜单", recordResponse = false)
    @ApiOperation(value = "获取角色菜单", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<RoleMenuResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "role_id", required = false) String roleId,
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "menu_id", required = false) String menuId
    ) {
        return roleMenuFeignClient.detail(roleId, menuId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "tree", description = "角色对应菜单树形结构", recordResponse = false)
    @ApiOperation(value = "角色对应菜单树形结构", httpMethod = "GET")
    @GetMapping(value = "/tree")
    public BusinessData<RoleMenuTreeResponseDTO> tree(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "role_id", required = false) String roleId,
            @ApiParam(required = false) @RequestParam(value = "menu_id", required = false) String menuId
    ) {
        MenuResponseDTO menuResponseDTO;
        if (ESStringUtils.isBlank(menuId)) {
            menuResponseDTO = menuServerService.root();
            if (ESObjectUtils.isNull(menuResponseDTO)) {
                throw new BusinessException("当前应用没有根节点菜单,请先创建");
            }
        } else {
            menuResponseDTO = menuServerService.detail(menuId);
            if (ESObjectUtils.isNull(menuResponseDTO)) {
                throw new BusinessException("菜单不存在");
            }
        }
        return roleMenuFeignClient.tree(roleId, menuResponseDTO.getId());
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "roleAuthority", description = "角色对应权限", recordResponse = false)
    @ApiOperation(value = "角色对应权限", httpMethod = "POST")
    @PostMapping(value = "/{role_id}/authority")
    public BusinessData<RoleAuthorityResponseDTO> roleAuthority(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @PathVariable(value = "role_id", required = false) String roleId
    ) {
        return roleMenuFeignClient.roleAuthority(roleId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "role_menu", operation = "roleListAuthority", description = "角色列表对应权限", recordResponse = false)
    @ApiOperation(value = "角色列表对应权限", httpMethod = "POST")
    @PostMapping(value = "/authority")
    public BusinessData<List<MenuAuthorityResponseDTO>> roleListAuthority(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @RequestBody(required = false) List<String> roleIdList
    ) {
        return roleMenuFeignClient.roleListAuthority(roleIdList);
    }
}

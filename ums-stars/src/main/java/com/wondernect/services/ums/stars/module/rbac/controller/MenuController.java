package com.wondernect.services.ums.stars.module.rbac.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.rbac.dto.menu.*;
import com.wondernect.stars.rbac.service.menu.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: MenuController
 * Author: chenxun
 * Date: 2020-02-21 14:08
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/wondernect/rbac/menu")
@Validated
@Api(tags = "权限服务-菜单")
public class MenuController {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private MenuService menuService;

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "create", description = "创建菜单")
    @ApiOperation(value = "创建菜单", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<MenuResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMenuRequestDTO saveMenuRequestDTO
    ) {
        if (ESStringUtils.isBlank(saveMenuRequestDTO.getParentMenuId())) {
            saveMenuRequestDTO.setParentMenuId(umsStarsConfigProperties.getRootMenuId());
        }
        return new BusinessData<>(menuService.create(saveMenuRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "update", description = "更新菜单")
    @ApiOperation(value = "更新菜单", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<MenuResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMenuRequestDTO saveMenuRequestDTO
    ) {
        return new BusinessData<>(menuService.update(id, saveMenuRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "delete", description = "删除菜单")
    @ApiOperation(value = "删除菜单", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        menuService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "detail", description = "获取菜单详情", recordResponse = false)
    @ApiOperation(value = "获取菜单详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<MenuResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(menuService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "list", description = "菜单列表", recordResponse = false)
    @ApiOperation(value = "菜单列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<MenuResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListMenuRequestDTO listMenuRequestDTO
    ) {
        if (ESStringUtils.isBlank(listMenuRequestDTO.getParentMenuId())) {
            listMenuRequestDTO.setParentMenuId(umsStarsConfigProperties.getRootMenuId());
        }
        return new BusinessData<>(menuService.list(listMenuRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "page", description = "菜单分页", recordResponse = false)
    @ApiOperation(value = "菜单分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<MenuResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageMenuRequestDTO pageMenuRequestDTO
    ) {
        if (ESStringUtils.isBlank(pageMenuRequestDTO.getParentMenuId())) {
            pageMenuRequestDTO.setParentMenuId(umsStarsConfigProperties.getRootMenuId());
        }
        return new BusinessData<>(menuService.page(pageMenuRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "root", description = "获取菜单根节点", recordResponse = false)
    @ApiOperation(value = "获取菜单根节点", httpMethod = "GET")
    @GetMapping(value = "/root")
    public BusinessData<MenuResponseDTO> root() {
        ListMenuRequestDTO listMenuRequestDTO = new ListMenuRequestDTO();
        listMenuRequestDTO.setParentMenuId(umsStarsConfigProperties.getRootMenuId());
        List<MenuResponseDTO> menuResponseDTOList = menuService.list(listMenuRequestDTO);
        if (CollectionUtils.isEmpty(menuResponseDTOList)) {
            return new BusinessData<>(BusinessError.SUCCESS);
        }
        return new BusinessData<>(menuResponseDTOList.get(0));
    }

    @AuthorizeServer
    @RequestLogger(module = "menu", operation = "tree", description = "菜单树形结构", recordResponse = false)
    @ApiOperation(value = "菜单树形结构", httpMethod = "GET")
    @GetMapping(value = "/{root_menu_id}/tree")
    public BusinessData<MenuTreeResponseDTO> tree(
            @ApiParam(required = true) @NotBlank(message = "根节点菜单不能为空") @PathVariable(value = "root_menu_id", required = false) String rootMenuId
    ) {
        return new BusinessData<>(menuService.tree(rootMenuId));
    }
}

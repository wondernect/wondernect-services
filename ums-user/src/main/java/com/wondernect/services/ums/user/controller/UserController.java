package com.wondernect.services.ums.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.services.ums.user.config.UserConfigProperties;
import com.wondernect.stars.user.dto.ListUserRequestDTO;
import com.wondernect.stars.user.dto.PageUserRequestDTO;
import com.wondernect.stars.user.dto.SaveLocalUserRequestDTO;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.feign.user.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户
 */
@Api(tags = "用户", description = "用户")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/user")
public class UserController {

    @Autowired
    private UserConfigProperties userConfigProperties;

    @Autowired
    private UserFeignClient userFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "激活", httpMethod = "POST")
    @PostMapping(value = "/{id}/enable")
    public BusinessData enable(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        return userFeignClient.enable(userId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "禁用", httpMethod = "POST")
    @PostMapping(value = "/{id}/disable")
    public BusinessData disable(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        return userFeignClient.disable(userId);
    }

    @ApiOperation(value = "自主注册", httpMethod = "POST")
    @PostMapping(value = "/regist")
    public BusinessData<UserResponseDTO> regist(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveLocalUserRequestDTO saveUserRequestDTO
    ) {
        saveUserRequestDTO.setRoleTypeId(userConfigProperties.getRoleTypeId());
        saveUserRequestDTO.setRoleId(userConfigProperties.getRoleId());
        return userFeignClient.create(saveUserRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<UserResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveLocalUserRequestDTO saveLocalUserRequestDTO
    ) {
        return userFeignClient.create(saveLocalUserRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<UserResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody SaveLocalUserRequestDTO saveUserRequestDTO
    ) {
        return userFeignClient.update(userId, saveUserRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        return userFeignClient.delete(userId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<UserResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "id", required = false) String userId
    ) {
        return userFeignClient.detail(userId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详情", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<UserResponseDTO> detailByUsername(
            @ApiParam(required = true) @NotBlank(message = "用户username不能为空") @RequestParam(value = "username", required = false) String username
    ) {
        return userFeignClient.detailByUsername(username);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<UserResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody ListUserRequestDTO listUserRequestDTO
    ) {
        return userFeignClient.list(listUserRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<UserResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody PageUserRequestDTO pageUserRequestDTO
    ) {
        return userFeignClient.page(pageUserRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "初始化本地用户导入导出item", httpMethod = "POST")
    @PostMapping(value = "/init_local_user_item")
    public BusinessData initLocalUserExcelItem(
            @ApiParam(required = false) @RequestParam(value = "force_update", required = false) Boolean forceUpdate
    ) {
        return userFeignClient.initLocalUserExcelItem(forceUpdate);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导出", httpMethod = "POST")
    @PostMapping(value = "/excel_data_export")
    public void excelDataExport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListUserRequestDTO listUserRequestDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userFeignClient.excelDataExport(templateId, listUserRequestDTO, request, response);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入", httpMethod = "POST")
    @PostMapping(value = "/excel_data_import")
    public void excelDataImport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotNull(message = "文件不能为空") @Validated @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userFeignClient.excelDataImport(templateId, file, request, response);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入模板下载", httpMethod = "GET")
    @GetMapping(value = "/excel_data_import_model")
    public void excelDataImportModel(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userFeignClient.excelDataImportModel(templateId, request, response);
    }
}

package com.wondernect.services.ums.database.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.database.dto.*;
import com.wondernect.stars.database.feign.databaseUserManage.DatabaseUserManageFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据库用户接口
 *
 * @author liyafei 2020-11-13 17:58:16
 **/
@RequestMapping(value = "/v1/ums/database_user_manage")
@RestController
@Validated
@Api(tags = "数据库用户服务")
public class DatabaseUserManageController {

    @Autowired
    private DatabaseUserManageFeignClient databaseUserManageFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<DatabaseUserManageResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserManageRequestDTO saveDatabaseUserManageRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.create(saveDatabaseUserManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<DatabaseUserManageResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserManageRequestDTO saveDatabaseUserManageRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.update(id, saveDatabaseUserManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        databaseUserManageFeignClient.delete(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<DatabaseUserManageResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.detail(id));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<DatabaseUserManageResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseUserManageRequestDTO listDatabaseUserManageRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.list(listDatabaseUserManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<DatabaseUserManageResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageDatabaseUserManageRequestDTO pageDatabaseUserManageRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.page(pageDatabaseUserManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "excel导出", httpMethod = "POST")
    @PostMapping(value = "/excel_data_export")
    public void excelDataExport(
            @ApiParam(required = true) @NotBlank(message = "excel导出服务id不能为空") @RequestParam(value = "export_service_identifier", required = false) String exportServiceIdentifier,
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseUserManageRequestDTO listDatabaseUserManageRequestDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        databaseUserManageFeignClient.excelDataExport(exportServiceIdentifier, listDatabaseUserManageRequestDTO, request, response);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "赋权限", httpMethod = "POST")
    @PostMapping(value = "/{type}/give_rights")
    public BusinessData<DatabaseUserManageResponseDTO> giveRights(
            @ApiParam(required = true) @NotNull(message = "1-只读权限，2-所有权限") @PathVariable(value = "type", required = false) Integer type,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseUserRequestDTO databaseUserRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.giveRights(type, databaseUserRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "收回权限", httpMethod = "POST")
    @PostMapping(value = "/revoke_rights")
    public BusinessData<DatabaseUserManageResponseDTO> revokeRights(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseUserRequestDTO databaseUserRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.revokeRights(databaseUserRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "测试连接", httpMethod = "POST")
    @PostMapping(value = "/test_connect")
    public BusinessData<TestConnectResponseDTO> testConnect(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseConnectRequestDTO databaseConnectRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.testConnect(databaseConnectRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "修改密码", httpMethod = "POST")
    @PostMapping(value = "/modify_password")
    public BusinessData<DatabaseUserManageResponseDTO> modifyPassword(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseModifyPasswordRequestDTO databaseModifyPasswordRequestDTO
    ) {
        return new BusinessData<>(databaseUserManageFeignClient.modifyPassword(databaseModifyPasswordRequestDTO));
    }

}
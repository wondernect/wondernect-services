package com.wondernect.services.ums.open.module.database.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.database.dto.DatabaseManageResponseDTO;
import com.wondernect.stars.database.dto.ListDatabaseManageRequestDTO;
import com.wondernect.stars.database.dto.PageDatabaseManageRequestDTO;
import com.wondernect.stars.database.dto.SaveDatabaseManageRequestDTO;
import com.wondernect.stars.database.feign.databaseManage.DatabaseManageFeignClient;
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
 * 数据库接口
 *
 * @author liyafei 2020-11-13 17:57:45
 **/
@RequestMapping(value = "/v1/ums/database/database_manage")
@RestController
@Validated
@Api(tags = "数据库服务-数据库名称接口")
public class DatabaseManageController {

    @Autowired
    private DatabaseManageFeignClient databaseManageFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<DatabaseManageResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseManageRequestDTO saveDatabaseManageRequestDTO
    ) {
        return databaseManageFeignClient.create(saveDatabaseManageRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<DatabaseManageResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseManageRequestDTO saveDatabaseManageRequestDTO
    ) {
        return databaseManageFeignClient.update(id, saveDatabaseManageRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return databaseManageFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<DatabaseManageResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return databaseManageFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<DatabaseManageResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseManageRequestDTO listDatabaseManageRequestDTO
    ) {
        return databaseManageFeignClient.list(listDatabaseManageRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<DatabaseManageResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageDatabaseManageRequestDTO pageDatabaseManageRequestDTO
    ) {
        return databaseManageFeignClient.page(pageDatabaseManageRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "初始化数据库", httpMethod = "POST")
    @PostMapping(value = "/init_database")
    public BusinessData<DatabaseManageResponseDTO> initDatabase(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "id", required = false) String id
    ) {
        return databaseManageFeignClient.initDatabase(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "查询用户已经有那些数据库的权限列表", httpMethod = "POST")
    @PostMapping(value = "/user_has_rights_list")
    public BusinessData<List<DatabaseManageResponseDTO>> userHasRightsList(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "database_user_id", required = false) String databaseUserId
    ) {
        return databaseManageFeignClient.userHasRightsList(databaseUserId);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "查询用户没有那些数据库的权限列表", httpMethod = "POST")
    @PostMapping(value = "/user_no_rights_list")
    public BusinessData<List<DatabaseManageResponseDTO>> userNoRightsList(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "database_user_id", required = false) String databaseUserId
    ) {
        return databaseManageFeignClient.userNoRightsList(databaseUserId);
    }
}
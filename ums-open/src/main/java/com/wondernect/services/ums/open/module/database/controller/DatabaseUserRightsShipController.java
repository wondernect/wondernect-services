package com.wondernect.services.ums.open.module.database.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.database.dto.*;
import com.wondernect.stars.database.feign.databaseUserRightsShip.DatabaseUserRightsShipFeignClient;
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
 * 数据库用户权限关系表接口
 *
 * @author 李亚飞 2020-12-02 14:25:42
 **/
@RequestMapping(value = "/v1/ums/open/database/database_user_rights_ship")
@RestController
@Validated
@Api(tags = "数据库服务-用户权限关系接口")
public class DatabaseUserRightsShipController {

    @Autowired
    private DatabaseUserRightsShipFeignClient databaseUserRightsShipFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<DatabaseUserRightsShipResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return databaseUserRightsShipFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<DatabaseUserRightsShipResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseUserRightsShipRequestDTO listDatabaseUserRightsShipRequestDTO
    ) {
        return databaseUserRightsShipFeignClient.list(listDatabaseUserRightsShipRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<DatabaseUserRightsShipResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageDatabaseUserRightsShipRequestDTO pageDatabaseUserRightsShipRequestDTO
    ) {
        return databaseUserRightsShipFeignClient.page(pageDatabaseUserRightsShipRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "赋权限", httpMethod = "POST")
    @PostMapping(value = "/{type}/give_rights")
    public BusinessData<DatabaseUserRightsShipResponseDTO> giveRights(
            @ApiParam(required = true) @NotNull(message = "1-只读权限，2-所有权限") @PathVariable(value = "type", required = false) Integer type,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        return databaseUserRightsShipFeignClient.giveRights(type, saveDatabaseUserRightsShipRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "收回权限", httpMethod = "POST")
    @PostMapping(value = "/revoke_rights")
    public BusinessData revokeRights(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        return databaseUserRightsShipFeignClient.revokeRights(saveDatabaseUserRightsShipRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "测试连接", httpMethod = "POST")
    @PostMapping(value = "/test_connect")
    public BusinessData<TestConnectResponseDTO> testConnect(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseUserRequestDTO databaseUserRequestDTO
    ) {
        return databaseUserRightsShipFeignClient.testConnect(databaseUserRequestDTO);
    }
}
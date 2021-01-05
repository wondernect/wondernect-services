package com.wondernect.services.ums.stars.module.database.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.services.ums.stars.module.database.service.DatabaseUserRightsShipClientService;
import com.wondernect.stars.database.dto.*;
import com.wondernect.stars.database.service.databaseUserRightsShip.DatabaseUserRightsShipService;
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
@RequestMapping(value = "/v1/wondernect/database/database_user_rights_ship")
@RestController
@Validated
@Api(tags = "数据库服务-数据库用户权限关系接口")
public class DatabaseUserRightsShipController {

    @Autowired
    private DatabaseUserRightsShipService databaseUserRightsShipService;

    @Autowired
    private DatabaseUserRightsShipClientService databaseUserRightsShipClientService;

    /*@ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<DatabaseUserRightsShipResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipService.create(saveDatabaseUserRightsShipRequestDTO));
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<DatabaseUserRightsShipResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipService.update(id, saveDatabaseUserRightsShipRequestDTO));
    }

    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        databaseUserRightsShipService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }*/

    @AuthorizeServer
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<DatabaseUserRightsShipResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(databaseUserRightsShipService.findById(id));
    }

    @AuthorizeServer
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<DatabaseUserRightsShipResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseUserRightsShipRequestDTO listDatabaseUserRightsShipRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipService.list(listDatabaseUserRightsShipRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<DatabaseUserRightsShipResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageDatabaseUserRightsShipRequestDTO pageDatabaseUserRightsShipRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipService.page(pageDatabaseUserRightsShipRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "赋权限", httpMethod = "POST")
    @PostMapping(value = "/{type}/give_rights")
    public BusinessData<DatabaseUserRightsShipResponseDTO> giveRights(
            @ApiParam(required = true) @NotNull(message = "1-只读权限，2-所有权限") @PathVariable(value = "type", required = false) Integer type,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipClientService.giveRights(type, saveDatabaseUserRightsShipRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "收回权限", httpMethod = "POST")
    @PostMapping(value = "/revoke_rights")
    public BusinessData revokeRights(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO
    ) {
        databaseUserRightsShipClientService.revokeRights(saveDatabaseUserRightsShipRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @ApiOperation(value = "测试连接", httpMethod = "POST")
    @PostMapping(value = "/test_connect")
    public BusinessData<TestConnectResponseDTO> testConnect(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) DatabaseUserRequestDTO databaseUserRequestDTO
    ) {
        return new BusinessData<>(databaseUserRightsShipClientService.testConnect(databaseUserRequestDTO));
    }
}
package com.wondernect.services.ums.database.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.database.dto.DatabaseRootManageResponseDTO;
import com.wondernect.stars.database.dto.ListDatabaseRootManageRequestDTO;
import com.wondernect.stars.database.dto.PageDatabaseRootManageRequestDTO;
import com.wondernect.stars.database.dto.SaveDatabaseRootManageRequestDTO;
import com.wondernect.stars.database.feign.databaseRootManage.DatabaseRootManageFeignClient;
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
 * @author 李亚飞 2020-12-02 15:06:23
 **/
@RequestMapping(value = "/v1/ums/database/database_root_manage")
@RestController
@Validated
@Api(tags = "数据库管理接口")
public class DatabaseRootManageController {

    @Autowired
    private DatabaseRootManageFeignClient databaseRootManageFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<DatabaseRootManageResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseRootManageRequestDTO saveDatabaseRootManageRequestDTO
    ) {
        return new BusinessData<>(databaseRootManageFeignClient.create(saveDatabaseRootManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<DatabaseRootManageResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveDatabaseRootManageRequestDTO saveDatabaseRootManageRequestDTO
    ) {
        return new BusinessData<>(databaseRootManageFeignClient.update(id, saveDatabaseRootManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        databaseRootManageFeignClient.delete(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<DatabaseRootManageResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(databaseRootManageFeignClient.detail(id));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<DatabaseRootManageResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListDatabaseRootManageRequestDTO listDatabaseRootManageRequestDTO
    ) {
        return new BusinessData<>(databaseRootManageFeignClient.list(listDatabaseRootManageRequestDTO));
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<DatabaseRootManageResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageDatabaseRootManageRequestDTO pageDatabaseRootManageRequestDTO
    ) {
        return new BusinessData<>(databaseRootManageFeignClient.page(pageDatabaseRootManageRequestDTO));
    }
}
package com.wondernect.services.ums.stars.module.app.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeAccessType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.app.dto.*;
import com.wondernect.stars.app.service.AppService;
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
 * 应用接口
 *
 * @author chenxun 2020-09-13 23:02:01
 **/
@RequestMapping(value = "/v1/wondernect/app")
@RestController
@Validated
@Api(tags = "应用服务-应用接口")
public class AppController {

    @Autowired
    private AppService appService;

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @RequestLogger(module = "app", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<AppResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveAppRequestDTO saveAppRequestDTO
    ) {
        return new BusinessData<>(appService.create(saveAppRequestDTO));
    }

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @RequestLogger(module = "app", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<AppResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveAppRequestDTO saveAppRequestDTO
    ) {
        return new BusinessData<>(appService.update(id, saveAppRequestDTO));
    }

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @RequestLogger(module = "app", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        appService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "app", operation = "detail", description = "获取详细信息")
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<AppResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(appService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "app", operation = "auth", description = "认证应用密钥")
    @ApiOperation(value = "认证应用密钥", httpMethod = "POST")
    @PostMapping(value = "/auth")
    public BusinessData auth(
            @ApiParam(required = true) @NotNull(message = "认证请求参数不能为空") @Validated @RequestBody(required = false) AuthAppRequestDTO authAppRequestDTO
    ) {
        appService.auth(authAppRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "app", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<AppResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListAppRequestDTO listAppRequestDTO
    ) {
        return new BusinessData<>(appService.list(listAppRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "app", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<AppResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageAppRequestDTO pageAppRequestDTO
    ) {
        return new BusinessData<>(appService.page(pageAppRequestDTO));
    }
}
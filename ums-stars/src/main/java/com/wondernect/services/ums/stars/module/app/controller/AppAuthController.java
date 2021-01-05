package com.wondernect.services.ums.stars.module.app.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeAccessType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.app.dto.auth.AppAuthResponseDTO;
import com.wondernect.stars.app.dto.auth.ListAppAuthRequestDTO;
import com.wondernect.stars.app.dto.auth.PageAppAuthRequestDTO;
import com.wondernect.stars.app.dto.auth.SaveAppAuthRequestDTO;
import com.wondernect.stars.app.service.AppAuthService;
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
 * 应用访问认证接口
 *
 * @author chenxun 2020-12-29 16:28:23
 **/
@RequestMapping(value = "/v1/wondernect/app/auth")
@RestController
@Validated
@Api(tags = "应用服务-应用访问认证接口")
public class AppAuthController {

    @Autowired
    private AppAuthService appAuthService;

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<AppAuthResponseDTO> create(
            @ApiParam(value = "创建请求对象", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveAppAuthRequestDTO saveAppAuthRequestDTO
    ) {
        return new BusinessData<>(appAuthService.create(saveAppAuthRequestDTO));
    }

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<AppAuthResponseDTO> update(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(value = "更新请求对象", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveAppAuthRequestDTO saveAppAuthRequestDTO
    ) {
        return new BusinessData<>(appAuthService.update(id, saveAppAuthRequestDTO));
    }

    @AuthorizeServer(accessType = AuthorizeAccessType.WRITE)
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        appAuthService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @ApiOperation(value = "获取详细信息", notes = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<AppAuthResponseDTO> detail(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(appAuthService.findById(id));
    }

    @AuthorizeServer
    @ApiOperation(value = "检查是否存在(返回缓存基本信息)", notes = "检查是否存在(返回缓存基本信息)", httpMethod = "GET")
    @GetMapping(value = "/exist")
    public BusinessData<AppAuthResponseDTO> exist(
            @ApiParam(value = "应用id", required = true) @NotBlank(message = "应用id不能为空") @PathVariable(value = "app_id", required = false) String appId,
            @ApiParam(value = "用户id", required = true) @NotBlank(message = "用户id不能为空") @PathVariable(value = "user_id", required = false) String userId
    ) {
        return new BusinessData<>(appAuthService.existByAppIdAndUserId(appId, userId));
    }

    @AuthorizeServer
    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<AppAuthResponseDTO>> list(
            @ApiParam(value = "列表请求对象", required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListAppAuthRequestDTO listAppAuthRequestDTO
    ) {
        return new BusinessData<>(appAuthService.list(listAppAuthRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "分页", notes = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<AppAuthResponseDTO>> page(
            @ApiParam(value = "分页请求对象", required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageAppAuthRequestDTO pageAppAuthRequestDTO
    ) {
        return new BusinessData<>(appAuthService.page(pageAppAuthRequestDTO));
    }
}
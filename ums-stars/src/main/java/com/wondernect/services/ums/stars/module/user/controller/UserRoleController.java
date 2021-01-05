package com.wondernect.services.ums.stars.module.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.user.dto.userrole.ListUserRoleRequestDTO;
import com.wondernect.stars.user.dto.userrole.PageUserRoleRequestDTO;
import com.wondernect.stars.user.dto.userrole.UserRoleRequestDTO;
import com.wondernect.stars.user.dto.userrole.UserRoleResponseDTO;
import com.wondernect.stars.user.service.userrole.UserRoleService;
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
 * 用户角色接口
 *
 * @author chenxun 2020-06-28 21:46:03
 **/
@RequestMapping(value = "/v1/wondernect/user/role")
@RestController
@Validated
@Api(tags = "用户服务-用户-角色", description = "用户-角色")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @AuthorizeServer
    @RequestLogger(module = "user_role", operation = "create", description = "新增")
    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping(value = "/add")
    public BusinessData create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) UserRoleRequestDTO userRoleRequestDTO
    ) {
        userRoleService.add(userRoleRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "user_role", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) UserRoleRequestDTO userRoleRequestDTO
    ) {
        userRoleService.delete(userRoleRequestDTO);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "user_role", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/detail")
    public BusinessData<UserRoleResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "用户id不能为空") @RequestParam(value = "user_id", required = false) String userId,
            @ApiParam(required = true) @NotBlank(message = "角色id不能为空") @RequestParam(value = "role_id", required = false) String roleId
    ) {
        return new BusinessData<>(userRoleService.findByUserIdAndRoleId(userId, roleId));
    }

    @AuthorizeServer
    @RequestLogger(module = "user_role", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<UserRoleResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListUserRoleRequestDTO listUserRoleRequestDTO
    ) {
        return new BusinessData<>(userRoleService.list(listUserRoleRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "user_role", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<UserRoleResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageUserRoleRequestDTO pageUserRoleRequestDTO
    ) {
        return new BusinessData<>(userRoleService.page(pageUserRoleRequestDTO));
    }
}
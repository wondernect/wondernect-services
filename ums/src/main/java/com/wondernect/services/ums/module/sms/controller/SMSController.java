package com.wondernect.services.ums.module.sms.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.sms.feign.sms.SmsFeignClient;
import com.wondernect.stars.sms.sms.dto.ListSMSRequestDTO;
import com.wondernect.stars.sms.sms.dto.PageSMSRequestDTO;
import com.wondernect.stars.sms.sms.dto.SMSResponseDTO;
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
 * 短信接口
 *
 * @author chenxun 2020-12-27 11:53:25
 **/
@RequestMapping(value = "/v1/ums/sms")
@RestController
@Validated
@Api(tags = "短信服务-短信接口")
public class SMSController {

    @Autowired
    private SmsFeignClient smsFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "sms", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return smsFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "sms", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", notes = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<SMSResponseDTO> detail(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return smsFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "sms", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<SMSResponseDTO>> list(
            @ApiParam(value = "列表请求对象", required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListSMSRequestDTO listSMSRequestDTO
    ) {
        return smsFeignClient.list(listSMSRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "sms", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", notes = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<SMSResponseDTO>> page(
            @ApiParam(value = "分页请求对象", required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageSMSRequestDTO pageSMSRequestDTO
    ) {
        return smsFeignClient.page(pageSMSRequestDTO);
    }
}
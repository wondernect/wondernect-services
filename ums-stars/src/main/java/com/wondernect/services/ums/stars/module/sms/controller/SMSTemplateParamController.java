package com.wondernect.services.ums.stars.module.sms.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.sms.param.dto.ListSMSTemplateParamRequestDTO;
import com.wondernect.stars.sms.param.dto.PageSMSTemplateParamRequestDTO;
import com.wondernect.stars.sms.param.dto.SMSTemplateParamResponseDTO;
import com.wondernect.stars.sms.param.dto.SaveSMSTemplateParamRequestDTO;
import com.wondernect.stars.sms.param.service.SMSTemplateParamService;
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
 * 短信模板变量接口
 *
 * @author chenxun 2020-12-27 11:54:45
 **/
@RequestMapping(value = "/v1/wondernect/sms/template_param")
@RestController
@Validated
@Api(tags = "短信服务-短信模板变量接口")
public class SMSTemplateParamController {

    @Autowired
    private SMSTemplateParamService sMSTemplateParamService;

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "create", description = "创建")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<SMSTemplateParamResponseDTO> create(
            @ApiParam(value = "创建请求对象", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveSMSTemplateParamRequestDTO saveSMSTemplateParamRequestDTO
    ) {
        return new BusinessData<>(sMSTemplateParamService.create(saveSMSTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "update", description = "更新")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<SMSTemplateParamResponseDTO> update(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(value = "更新请求对象", required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveSMSTemplateParamRequestDTO saveSMSTemplateParamRequestDTO
    ) {
        return new BusinessData<>(sMSTemplateParamService.update(id, saveSMSTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        sMSTemplateParamService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", notes = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<SMSTemplateParamResponseDTO> detail(
            @ApiParam(value = "对象id", required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(sMSTemplateParamService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<SMSTemplateParamResponseDTO>> list(
            @ApiParam(value = "列表请求对象", required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListSMSTemplateParamRequestDTO listSMSTemplateParamRequestDTO
    ) {
        return new BusinessData<>(sMSTemplateParamService.list(listSMSTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "sms_template_param", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", notes = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<SMSTemplateParamResponseDTO>> page(
            @ApiParam(value = "分页请求对象", required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageSMSTemplateParamRequestDTO pageSMSTemplateParamRequestDTO
    ) {
        return new BusinessData<>(sMSTemplateParamService.page(pageSMSTemplateParamRequestDTO));
    }
}
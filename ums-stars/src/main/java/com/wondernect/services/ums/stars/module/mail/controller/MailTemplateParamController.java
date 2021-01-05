package com.wondernect.services.ums.stars.module.mail.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.mail.dto.param.ListMailTemplateParamRequestDTO;
import com.wondernect.stars.mail.dto.param.MailTemplateParamResponseDTO;
import com.wondernect.stars.mail.dto.param.PageMailTemplateParamRequestDTO;
import com.wondernect.stars.mail.dto.param.SaveMailTemplateParamRequestDTO;
import com.wondernect.stars.mail.service.param.MailTemplateParamService;
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
 * 邮件模板参数接口
 *
 * @author 王威 2020-11-23 15:55:11
 **/
@RequestMapping(value = "/v1/wondernect/mail/template_param")
@RestController
@Validated
@Api(tags = "邮件服务-邮件模板参数接口")
public class MailTemplateParamController {

    @Autowired
    private MailTemplateParamService mailTemplateParamService;

    @AuthorizeServer
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<MailTemplateParamResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMailTemplateParamRequestDTO saveMailTemplateParamRequestDTO
    ) {
        return new BusinessData<>(mailTemplateParamService.create(saveMailTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<MailTemplateParamResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMailTemplateParamRequestDTO saveMailTemplateParamRequestDTO
    ) {
        return new BusinessData<>(mailTemplateParamService.update(id, saveMailTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        mailTemplateParamService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<MailTemplateParamResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(mailTemplateParamService.findById(id));
    }

    @AuthorizeServer
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<MailTemplateParamResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListMailTemplateParamRequestDTO listMailTemplateParamRequestDTO
    ) {
        return new BusinessData<>(mailTemplateParamService.list(listMailTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<MailTemplateParamResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageMailTemplateParamRequestDTO pageMailTemplateParamRequestDTO
    ) {
        return new BusinessData<>(mailTemplateParamService.page(pageMailTemplateParamRequestDTO));
    }
}
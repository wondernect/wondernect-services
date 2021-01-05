package com.wondernect.services.ums.stars.module.mail.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.mail.dto.server.ListMailServerRequestDTO;
import com.wondernect.stars.mail.dto.server.MailServerResponseDTO;
import com.wondernect.stars.mail.dto.server.PageMailServerRequestDTO;
import com.wondernect.stars.mail.dto.server.SaveMailServerRequestDTO;
import com.wondernect.stars.mail.service.server.MailServerService;
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
 * 邮箱服务器接口
 *
 * @author 王威 2020-11-23 15:55:47
 **/
@RequestMapping(value = "/v1/wondernect/mail/server")
@RestController
@Validated
@Api(tags = "邮件服务-邮箱服务器接口")
public class MailServerController {

    @Autowired
    private MailServerService mailServerService;

    @AuthorizeServer
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<MailServerResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMailServerRequestDTO saveMailServerRequestDTO
    ) {
        return new BusinessData<>(mailServerService.create(saveMailServerRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<MailServerResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveMailServerRequestDTO saveMailServerRequestDTO
    ) {
        return new BusinessData<>(mailServerService.update(id, saveMailServerRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        mailServerService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<MailServerResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(mailServerService.findById(id));
    }

    @AuthorizeServer
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<MailServerResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListMailServerRequestDTO listMailServerRequestDTO
    ) {
        return new BusinessData<>(mailServerService.list(listMailServerRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<MailServerResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageMailServerRequestDTO pageMailServerRequestDTO
    ) {
        return new BusinessData<>(mailServerService.page(pageMailServerRequestDTO));
    }
}
package com.wondernect.services.ums.module.office.excel.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.office.excel.dto.template.ExcelTemplateResponseDTO;
import com.wondernect.stars.office.excel.dto.template.ListExcelTemplateRequestDTO;
import com.wondernect.stars.office.excel.dto.template.PageExcelTemplateRequestDTO;
import com.wondernect.stars.office.excel.dto.template.SaveExcelTemplateRequestDTO;
import com.wondernect.stars.office.feign.excel.template.ExcelTemplateFeignClient;
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
 * excel导入导出模板接口
 *
 * @author chenxun 2020-11-17 16:19:29
 **/
@RequestMapping(value = "/v1/ums/office/excel_template")
@RestController
@Validated
@Api(tags = "excel服务-模板接口")
public class ExcelTemplateController {

    @Autowired
    private ExcelTemplateFeignClient excelTemplateFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<ExcelTemplateResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelTemplateRequestDTO saveExcelTemplateRequestDTO
    ) {
        return excelTemplateFeignClient.create(saveExcelTemplateRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<ExcelTemplateResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelTemplateRequestDTO saveExcelTemplateRequestDTO
    ) {
        return excelTemplateFeignClient.update(id, saveExcelTemplateRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return excelTemplateFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<ExcelTemplateResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return excelTemplateFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<ExcelTemplateResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListExcelTemplateRequestDTO listExcelTemplateRequestDTO
    ) {
        return excelTemplateFeignClient.list(listExcelTemplateRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "excel_template", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<ExcelTemplateResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageExcelTemplateRequestDTO pageExcelTemplateRequestDTO
    ) {
        return excelTemplateFeignClient.page(pageExcelTemplateRequestDTO);
    }
}
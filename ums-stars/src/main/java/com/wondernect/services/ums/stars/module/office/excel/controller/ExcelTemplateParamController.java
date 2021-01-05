package com.wondernect.services.ums.stars.module.office.excel.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.office.excel.dto.param.*;
import com.wondernect.stars.office.excel.param.service.ExcelTemplateParamService;
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
 * excel导入导出模板配置接口
 *
 * @author chenxun 2020-11-17 16:20:19
 **/
@RequestMapping(value = "/v1/wondernect/office/excel_template_param")
@RestController
@Validated
@Api(tags = "excel服务-导入导出模板配置接口")
public class ExcelTemplateParamController {

    @Autowired
    private ExcelTemplateParamService excelTemplateParamService;

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "batchAdd", description = "批量添加")
    @ApiOperation(value = "批量添加", httpMethod = "POST")
    @PostMapping(value = "/batch_add")
    public BusinessData batchAdd(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) BatchAddExcelTemplateParamRequestDTO batchAddExcelTemplateParamRequestDTO
    ) {
        excelTemplateParamService.batchAdd(batchAddExcelTemplateParamRequestDTO);
        return new BusinessData<>(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<ExcelTemplateParamResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelTemplateParamRequestDTO saveExcelTemplateParamRequestDTO
    ) {
        return new BusinessData<>(excelTemplateParamService.create(saveExcelTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<ExcelTemplateParamResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelTemplateParamRequestDTO saveExcelTemplateParamRequestDTO
    ) {
        return new BusinessData<>(excelTemplateParamService.update(id, saveExcelTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        excelTemplateParamService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<ExcelTemplateParamResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(excelTemplateParamService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "detailByTemplateIdAndName", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail_by_template_id_and_name")
    public BusinessData<ExcelTemplateParamResponseDTO> detailByTemplateIdAndName(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotBlank(message = "属性名不能为空") @RequestParam(value = "name", required = false) String name
    ) {
        return new BusinessData<>(excelTemplateParamService.findByTemplateIdAndName(templateId, name));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<ExcelTemplateParamResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListExcelTemplateParamRequestDTO listExcelTemplateParamRequestDTO
    ) {
        return new BusinessData<>(excelTemplateParamService.list(listExcelTemplateParamRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_template_param", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<ExcelTemplateParamResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageExcelTemplateParamRequestDTO pageExcelTemplateParamRequestDTO
    ) {
        return new BusinessData<>(excelTemplateParamService.page(pageExcelTemplateParamRequestDTO));
    }
}
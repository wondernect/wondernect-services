package com.wondernect.services.ums.stars.module.office.excel.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.office.excel.dto.property.ExcelBeanPropertyResponseDTO;
import com.wondernect.stars.office.excel.dto.property.ListExcelBeanPropertyRequestDTO;
import com.wondernect.stars.office.excel.dto.property.PageExcelBeanPropertyRequestDTO;
import com.wondernect.stars.office.excel.dto.property.SaveExcelBeanPropertyRequestDTO;
import com.wondernect.stars.office.excel.property.service.ExcelBeanPropertyService;
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
 * excel导入导出实体类属性接口
 *
 * @author chenxun 2020-11-17 16:19:04
 **/
@RequestMapping(value = "/v1/wondernect/office/excel_bean_property")
@RestController
@Validated
@Api(tags = "excel服务-导入导出实体类属性接口")
public class ExcelBeanPropertyController {

    @Autowired
    private ExcelBeanPropertyService excelBeanPropertyService;

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<ExcelBeanPropertyResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelBeanPropertyRequestDTO saveExcelBeanPropertyRequestDTO
    ) {
        return new BusinessData<>(excelBeanPropertyService.create(saveExcelBeanPropertyRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<ExcelBeanPropertyResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelBeanPropertyRequestDTO saveExcelBeanPropertyRequestDTO
    ) {
        return new BusinessData<>(excelBeanPropertyService.update(id, saveExcelBeanPropertyRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        excelBeanPropertyService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<ExcelBeanPropertyResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(excelBeanPropertyService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "detailByBeanIdAndName", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/detail_by_bean_id_and_name")
    public BusinessData<ExcelBeanPropertyResponseDTO> detailByBeanIdAndName(
            @ApiParam(required = true) @NotBlank(message = "实体类id不能为空") @RequestParam(value = "beanId", required = false) String beanId,
            @ApiParam(required = true) @NotBlank(message = "属性名不能为空") @RequestParam(value = "name", required = false) String name
    ) {
        return new BusinessData<>(excelBeanPropertyService.findByBeanIdAndName(beanId, name));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<ExcelBeanPropertyResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListExcelBeanPropertyRequestDTO listExcelBeanPropertyRequestDTO
    ) {
        return new BusinessData<>(excelBeanPropertyService.list(listExcelBeanPropertyRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean_property", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<ExcelBeanPropertyResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageExcelBeanPropertyRequestDTO pageExcelBeanPropertyRequestDTO
    ) {
        return new BusinessData<>(excelBeanPropertyService.page(pageExcelBeanPropertyRequestDTO));
    }
}
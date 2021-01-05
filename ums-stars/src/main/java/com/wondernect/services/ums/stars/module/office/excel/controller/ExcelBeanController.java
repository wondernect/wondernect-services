package com.wondernect.services.ums.stars.module.office.excel.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.office.excel.bean.service.ExcelBeanService;
import com.wondernect.stars.office.excel.dto.bean.ExcelBeanResponseDTO;
import com.wondernect.stars.office.excel.dto.bean.ListExcelBeanRequestDTO;
import com.wondernect.stars.office.excel.dto.bean.PageExcelBeanRequestDTO;
import com.wondernect.stars.office.excel.dto.bean.SaveExcelBeanRequestDTO;
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
 * excel导入导出实体类接口
 *
 * @author chenxun 2020-11-17 16:18:31
 **/
@RequestMapping(value = "/v1/wondernect/office/excel_bean")
@RestController
@Validated
@Api(tags = "excel服务-导入导出实体类接口")
public class ExcelBeanController {

    @Autowired
    private ExcelBeanService excelBeanService;

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<ExcelBeanResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelBeanRequestDTO saveExcelBeanRequestDTO
    ) {
        return new BusinessData<>(excelBeanService.create(saveExcelBeanRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<ExcelBeanResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveExcelBeanRequestDTO saveExcelBeanRequestDTO
    ) {
        return new BusinessData<>(excelBeanService.update(id, saveExcelBeanRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        excelBeanService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<ExcelBeanResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(excelBeanService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "detailByBean", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/detail_by_bean")
    public BusinessData<ExcelBeanResponseDTO> detailByBean(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @RequestParam(value = "bean", required = false) String bean
    ) {
        return new BusinessData<>(excelBeanService.findByBean(bean));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<ExcelBeanResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListExcelBeanRequestDTO listExcelBeanRequestDTO
    ) {
        return new BusinessData<>(excelBeanService.list(listExcelBeanRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "excel_bean", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<ExcelBeanResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageExcelBeanRequestDTO pageExcelBeanRequestDTO
    ) {
        return new BusinessData<>(excelBeanService.page(pageExcelBeanRequestDTO));
    }
}
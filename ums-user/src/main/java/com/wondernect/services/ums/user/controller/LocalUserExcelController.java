package com.wondernect.services.ums.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.services.ums.user.service.LocalUserExcelExportService;
import com.wondernect.services.ums.user.service.LocalUserExcelImportService;
import com.wondernect.services.ums.user.service.LocalUserExcelInitService;
import com.wondernect.stars.user.dto.ListUserRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: UserController
 * Author: chenxun
 * Date: 2019/6/5 14:43
 * Description: 用户
 */
@Api(tags = "本地用户导入&导出", description = "本地用户导入&导出")
@Validated
@RestController
@RequestMapping(value = "/v1/ums/user")
public class LocalUserExcelController {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelController.class);

    @Autowired
    private LocalUserExcelInitService localUserExcelInitService;

    @Autowired
    private LocalUserExcelExportService localUserExcelExportService;

    @Autowired
    private LocalUserExcelImportService localUserExcelImportService;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "初始化本地用户导入导出item", httpMethod = "POST")
    @PostMapping(value = "/init_local_user_item")
    public BusinessData initLocalUserExcelItem(
            @ApiParam(required = false) @RequestParam(value = "force_update", required = false) Boolean forceUpdate
    ) {
        if (ESObjectUtils.isNull(forceUpdate)) {
            forceUpdate = false;
        }
        localUserExcelInitService.initLocalUserExcelItem(forceUpdate);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导出", httpMethod = "POST")
    @PostMapping(value = "/local_user_data_export")
    public void excelDataExport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListUserRequestDTO listUserRequestDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            localUserExcelExportService.excelDataExport(templateId, listUserRequestDTO, request, response);
        } catch (Exception e) {
            BusinessData.error(e.getMessage(), response);
        }
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入", httpMethod = "POST")
    @PostMapping(value = "/local_user_data_import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void excelDataImport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotNull(message = "导入文件不能为空") @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            localUserExcelImportService.excelDataImport(templateId, file.getInputStream(), request, response);
        } catch (Exception e) {
            BusinessData.error(e.getMessage(), response);
        }
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入模板下载", httpMethod = "GET")
    @GetMapping(value = "/local_user_data_import_model")
    public void excelDataImportModel(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            localUserExcelExportService.excelDataImportModel(templateId, request, response);
        } catch (Exception e) {
            BusinessData.error(e.getMessage(), response);
        }
    }

    /**
     * 导出文件(请求响应)
     */
    // private void exportExcel(Response feignResponse, String fileName, HttpServletRequest request, HttpServletResponse response) {
    //     try {
    //         InputStream inputStream = feignResponse.body().asInputStream();
    //         fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    //         response.setHeader("content-type", "application/vnd.ms-excel");
    //         response.setHeader("content-disposition", "attachment;filename=" + fileName);
    //         byte[] bytes = new byte[1024];
    //         int len;
    //         while ((len = inputStream.read(bytes)) != -1) {
    //             response.getOutputStream().write(bytes, 0, len);
    //         }
    //         inputStream.close();
    //         response.getOutputStream().close();
    //         response.getOutputStream().flush();
    //     } catch (IOException e) {
    //         logger.error("Excel export failed, message={}, stacktrace={}", e.getLocalizedMessage(), e.getCause());
    //     }
    // }
}

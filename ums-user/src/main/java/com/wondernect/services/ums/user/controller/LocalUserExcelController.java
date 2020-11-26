package com.wondernect.services.ums.user.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.stars.user.dto.ListUserRequestDTO;
import com.wondernect.stars.user.feign.user.LocalUserExcelFeignClient;
import feign.Response;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    private LocalUserExcelFeignClient localUserExcelFeignClient;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "初始化本地用户导入导出item", httpMethod = "POST")
    @PostMapping(value = "/init_local_user_item")
    public BusinessData initLocalUserExcelItem(
            @ApiParam(required = false) @RequestParam(value = "force_update", required = false) Boolean forceUpdate
    ) {
        return localUserExcelFeignClient.initLocalUserExcelItem(forceUpdate);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导出", httpMethod = "POST")
    @PostMapping(value = "/excel_data_export")
    public void excelDataExport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListUserRequestDTO listUserRequestDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Response feignResponse = localUserExcelFeignClient.excelDataExport(templateId, listUserRequestDTO);
        exportExcel(feignResponse, "本地用户信息.xlsx", request, response);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入", httpMethod = "POST")
    @PostMapping(value = "/excel_data_import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void excelDataImport(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            @ApiParam(required = true) @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Response feignResponse = localUserExcelFeignClient.excelDataImport(templateId, file);
        exportExcel(feignResponse, "本地用户信息导入失败.xlsx", request, response);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @ApiOperation(value = "本地用户导入模板下载", httpMethod = "GET")
    @GetMapping(value = "/excel_data_import_model")
    public void excelDataImportModel(
            @ApiParam(required = true) @NotBlank(message = "模板id不能为空") @RequestParam(value = "template_id", required = false) String templateId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Response feignResponse = localUserExcelFeignClient.excelDataImportModel(templateId);
        exportExcel(feignResponse, templateId + ".xlsx", request, response);
    }

    /**
     * 导出文件(请求响应)
     */
    private void exportExcel(Response feignResponse, String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream inputStream = feignResponse.body().asInputStream();
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("content-type", "application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                response.getOutputStream().write(bytes, 0, len);
            }
            inputStream.close();
            response.getOutputStream().close();
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error("Excel export failed, message={}, stacktrace={}", e.getLocalizedMessage(), e.getCause());
        }
    }
}

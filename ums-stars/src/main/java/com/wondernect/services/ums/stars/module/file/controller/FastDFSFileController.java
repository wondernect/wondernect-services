package com.wondernect.services.ums.stars.module.file.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.file.dto.FileResponseDTO;
import com.wondernect.stars.file.dto.ListFileRequestDTO;
import com.wondernect.stars.file.dto.PageFileRequestDTO;
import com.wondernect.stars.file.service.fastdfs.FastDFSFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: LocalFileController
 * Author: chenxun
 * Date: 2019/3/28 16:12
 * Description: local file controller
 */
@RestController
@RequestMapping(value = "/v1/wondernect/file/fast_dfs")
@Validated
@Api(tags = "文件服务-FastDFS文件服务")
public class FastDFSFileController {

    @Autowired
    private FastDFSFileService fastDFSFileService;

    @AuthorizeServer
    @RequestLogger(module = "fast_dfs", operation = "upload", description = "上传文件")
    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BusinessData<FileResponseDTO> upload(
            @ApiParam(required = false, allowableValues = "IMAGE, IMAGE_FILE, VOICE, VIDEO, FILE") @NotBlank(message = "文件类型不能为空") @RequestParam(value = "file_type", required = false) String fileType,
            @ApiParam(required = true) @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return new BusinessData<>(fastDFSFileService.upload(file, "", "", fileType, new HashMap<>()));
    }

    @AuthorizeServer
    @RequestLogger(module = "fast_dfs", operation = "deleteById", description = "删除文件")
    @ApiOperation(value = "删除文件", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData deleteById(
            @ApiParam(required = true) @NotBlank(message = "文件id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        fastDFSFileService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "fast_dfs", operation = "detail", description = "获取文件信息", recordResponse = false)
    @ApiOperation(value = "获取文件信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<FileResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "文件id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(fastDFSFileService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "fast_dfs", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<FileResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @RequestBody(required = false) ListFileRequestDTO listFileRequestDTO
    ) {
        return new BusinessData<>(fastDFSFileService.list(listFileRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "fast_dfs", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<FileResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @RequestBody(required = false) PageFileRequestDTO pageFileRequestDTO
    ) {
        return new BusinessData<>(fastDFSFileService.page(pageFileRequestDTO));
    }
}

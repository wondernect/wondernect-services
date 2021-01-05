package com.wondernect.services.ums.open.module.file.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeRoleType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeType;
import com.wondernect.elements.authorize.context.interceptor.AuthorizeUserRole;
import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.file.dto.FileResponseDTO;
import com.wondernect.stars.file.dto.ListFileRequestDTO;
import com.wondernect.stars.file.dto.LocalFilePathResponseDTO;
import com.wondernect.stars.file.dto.PageFileRequestDTO;
import com.wondernect.stars.file.feign.local.LocalFileFeignClient;
import com.wondernect.stars.file.feign.path.LocalFilePathServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: LocalFileController
 * Author: chenxun
 * Date: 2019/3/28 16:12
 * Description: local file controller
 */
@RestController
@RequestMapping(value = "/v1/ums/file/local")
@Validated
@Api(tags = "文件服务-本地文件服务")
public class LocalFileController {

    @Autowired
    private LocalFileFeignClient localFileFeignClient;

    @Autowired
    private LocalFilePathServerService localFilePathServerService;

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "upload", description = "上传文件")
    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BusinessData<FileResponseDTO> upload(
            @ApiParam(required = false, allowableValues = "IMAGE, IMAGE_FILE, VOICE, VIDEO, FILE") @NotBlank(message = "文件类型不能为空") @RequestParam(value = "file_type", required = false) String fileType,
            @ApiParam(required = false) @RequestParam(value = "path_id", required = false) String pathId,
            @ApiParam(required = true) @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        LocalFilePathResponseDTO localFilePathResponseDTO;
        if (ESStringUtils.isBlank(pathId)) {
            localFilePathResponseDTO = localFilePathServerService.root();
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("当前应用没有根节点文件存储路径,请先创建");
            }
        } else {
            localFilePathResponseDTO = localFilePathServerService.detail(pathId);
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("文件存储路径不存在");
            }
        }
        if (ESStringUtils.isBlank(localFilePathResponseDTO.getSubFilePath())) {
            throw new BusinessException("文件存储路径为空");
        }
        return localFileFeignClient.upload(fileType, localFilePathResponseDTO.getId(), file);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "wechatUpload", description = "上传文件(微信小程序)")
    @ApiOperation(value = "上传文件(微信小程序)", httpMethod = "POST")
    @PostMapping(value = "/wechat/upload")
    public BusinessData<FileResponseDTO> wechatUpload(
            @ApiParam(required = false, allowableValues = "IMAGE, IMAGE_FILE, VOICE, VIDEO, FILE") @NotBlank(message = "文件类型不能为空") @RequestParam(value = "file_type", required = false) String fileType,
            @ApiParam(required = false) @RequestParam(value = "path_id", required = false) String pathId,
            @ApiParam(required = true) @NotBlank(message = "文件获取标识不能为空") @RequestParam(value = "file_key", required = false) String fileKey,
            HttpServletRequest httpServletRequest
    ) {
        LocalFilePathResponseDTO localFilePathResponseDTO;
        if (ESStringUtils.isBlank(pathId)) {
            localFilePathResponseDTO = localFilePathServerService.root();
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("当前应用没有根节点文件存储路径,请先创建");
            }
        } else {
            localFilePathResponseDTO = localFilePathServerService.detail(pathId);
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("文件存储路径不存在");
            }
        }
        if (ESStringUtils.isBlank(localFilePathResponseDTO.getSubFilePath())) {
            throw new BusinessException("文件存储路径为空");
        }
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        MultipartFile file = multipartHttpServletRequest.getFile(fileKey);
        return localFileFeignClient.upload(fileType, localFilePathResponseDTO.getId(), file);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "delete", description = "删除文件")
    @ApiOperation(value = "删除文件", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "文件id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return localFileFeignClient.delete(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "detail", description = "获取文件信息", recordResponse = false)
    @ApiOperation(value = "获取文件信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<FileResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "文件id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return localFileFeignClient.detail(id);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<FileResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @RequestBody(required = false) ListFileRequestDTO listFileRequestDTO
    ) {
        return localFileFeignClient.list(listFileRequestDTO);
    }

    @AuthorizeUserRole(authorizeType = AuthorizeType.EXPIRES_TOKEN, authorizeRoleType = AuthorizeRoleType.ONLY_AUTHORIZE)
    @RequestLogger(module = "local", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<FileResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @RequestBody(required = false) PageFileRequestDTO pageFileRequestDTO
    ) {
        return localFileFeignClient.page(pageFileRequestDTO);
    }
}

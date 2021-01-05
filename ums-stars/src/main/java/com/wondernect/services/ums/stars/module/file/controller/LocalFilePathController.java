package com.wondernect.services.ums.stars.module.file.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.logger.request.RequestLogger;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.file.dto.*;
import com.wondernect.stars.file.service.localfilepath.LocalFilePathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: RoleTypeController
 * Author: chenxun
 * Date: 2020-02-21 14:07
 * Description:
 */
@RestController
@RequestMapping(value = "/v1/wondernect/file/path")
@Validated
@Api(tags = "文件服务-文件夹")
public class LocalFilePathController {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private LocalFilePathService localFilePathService;

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "create", description = "创建")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<LocalFilePathResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveLocalFilePathRequestDTO saveLocalFilePathRequestDTO
    ) {
        if (ESStringUtils.isBlank(saveLocalFilePathRequestDTO.getParentPathId())) {
            saveLocalFilePathRequestDTO.setParentPathId(umsStarsConfigProperties.getRootFilePathId());
        }
        return new BusinessData<>(localFilePathService.create(saveLocalFilePathRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "update", description = "更新")
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<LocalFilePathResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveLocalFilePathRequestDTO saveLocalFilePathRequestDTO
    ) {
        if (ESStringUtils.isBlank(saveLocalFilePathRequestDTO.getParentPathId())) {
            saveLocalFilePathRequestDTO.setParentPathId(umsStarsConfigProperties.getRootFilePathId());
        }
        return new BusinessData<>(localFilePathService.update(id, saveLocalFilePathRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "delete", description = "删除")
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        localFilePathService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "detail", description = "获取详细信息", recordResponse = false)
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<LocalFilePathResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "请求参数不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(localFilePathService.findById(id));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "list", description = "列表", recordResponse = false)
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<LocalFilePathResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) ListLocalFilePathRequestDTO listLocalFilePathRequestDTO
    ) {
        if (ESStringUtils.isBlank(listLocalFilePathRequestDTO.getParentPathId())) {
            listLocalFilePathRequestDTO.setParentPathId(umsStarsConfigProperties.getRootFilePathId());
        }
        return new BusinessData<>(localFilePathService.list(listLocalFilePathRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "page", description = "分页", recordResponse = false)
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<LocalFilePathResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) PageLocalFilePathRequestDTO pageLocalFilePathRequestDTO
    ) {
        if (ESStringUtils.isBlank(pageLocalFilePathRequestDTO.getParentPathId())) {
            pageLocalFilePathRequestDTO.setParentPathId(umsStarsConfigProperties.getRootFilePathId());
        }
        return new BusinessData<>(localFilePathService.page(pageLocalFilePathRequestDTO));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "root", description = "获取根节点", recordResponse = false)
    @ApiOperation(value = "获取根节点", httpMethod = "GET")
    @GetMapping(value = "/root")
    public BusinessData<LocalFilePathResponseDTO> root() {
        ListLocalFilePathRequestDTO listLocalFilePathRequestDTO = new ListLocalFilePathRequestDTO();
        listLocalFilePathRequestDTO.setParentPathId(umsStarsConfigProperties.getRootFilePathId());
        listLocalFilePathRequestDTO.setIsDeleted(false);
        List<LocalFilePathResponseDTO> localFilePathResponseDTOList = localFilePathService.list(listLocalFilePathRequestDTO);
        if (CollectionUtils.isEmpty(localFilePathResponseDTOList)) {
            return new BusinessData<>(BusinessError.SUCCESS);
        }
        return new BusinessData<>(localFilePathResponseDTOList.get(0));
    }

    @AuthorizeServer
    @RequestLogger(module = "local_file_path", operation = "tree", description = "树形结构", recordResponse = false)
    @ApiOperation(value = "树形结构", httpMethod = "GET")
    @GetMapping(value = "/{root_file_path_id}/tree")
    public BusinessData<LocalFilePathTreeResponseDTO> tree(
            @ApiParam(required = true) @NotBlank(message = "根节点文件路径不能为空") @PathVariable(value = "root_file_path_id", required = false) String rootFilePathId
    ) {
        return new BusinessData<>(localFilePathService.tree(rootFilePathId));
    }
}

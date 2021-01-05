package com.wondernect.services.ums.stars.module.logger.controller;

import com.wondernect.elements.authorize.context.interceptor.AuthorizeServer;
import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.rdb.response.PageResponseData;
import com.wondernect.stars.logger.dto.ListRequestLogRequestDTO;
import com.wondernect.stars.logger.dto.PageRequestLogRequestDTO;
import com.wondernect.stars.logger.dto.RequestLogResponseDTO;
import com.wondernect.stars.logger.dto.SaveRequestLogRequestDTO;
import com.wondernect.stars.logger.service.RequestLogService;
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
 * 日志接口
 *
 * @author chenxun 2020-12-17 14:58:52
 **/
@RequestMapping(value = "/v1/wondernect/log/request_log")
@RestController
@Validated
@Api(tags = "日志服务-请求日志接口")
public class RequestLogController {

    @Autowired
    private RequestLogService requestLogService;

    @AuthorizeServer
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public BusinessData<RequestLogResponseDTO> create(
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRequestLogRequestDTO saveRequestLogRequestDTO
    ) {
        return new BusinessData<>(requestLogService.create(saveRequestLogRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/{id}/update")
    public BusinessData<RequestLogResponseDTO> update(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id,
            @ApiParam(required = true) @NotNull(message = "请求参数不能为空") @Validated @RequestBody(required = false) SaveRequestLogRequestDTO saveRequestLogRequestDTO
    ) {
        return new BusinessData<>(requestLogService.update(id, saveRequestLogRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "删除", httpMethod = "POST")
    @PostMapping(value = "/{id}/delete")
    public BusinessData delete(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        requestLogService.deleteById(id);
        return new BusinessData(BusinessError.SUCCESS);
    }

    @AuthorizeServer
    @ApiOperation(value = "获取详细信息", httpMethod = "GET")
    @GetMapping(value = "/{id}/detail")
    public BusinessData<RequestLogResponseDTO> detail(
            @ApiParam(required = true) @NotBlank(message = "对象id不能为空") @PathVariable(value = "id", required = false) String id
    ) {
        return new BusinessData<>(requestLogService.findById(id));
    }

    @AuthorizeServer
    @ApiOperation(value = "列表", httpMethod = "POST")
    @PostMapping(value = "/list")
    public BusinessData<List<RequestLogResponseDTO>> list(
            @ApiParam(required = true) @NotNull(message = "列表请求参数不能为空") @Validated @RequestBody(required = false) ListRequestLogRequestDTO listRequestLogRequestDTO
    ) {
        return new BusinessData<>(requestLogService.list(listRequestLogRequestDTO));
    }

    @AuthorizeServer
    @ApiOperation(value = "分页", httpMethod = "POST")
    @PostMapping(value = "/page")
    public BusinessData<PageResponseData<RequestLogResponseDTO>> page(
            @ApiParam(required = true) @NotNull(message = "分页请求参数不能为空") @Validated @RequestBody(required = false) PageRequestLogRequestDTO pageRequestLogRequestDTO
    ) {
        return new BusinessData<>(requestLogService.page(pageRequestLogRequestDTO));
    }
}
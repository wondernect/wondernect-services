package com.wondernect.services.ums.open.module.user.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelBooleanItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelStringItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelTimestampItemHandler;
import com.wondernect.elements.easyoffice.excel.service.ESExcelExportResponseService;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelDTO;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelGenderItemHandler;
import com.wondernect.stars.office.excel.dto.param.ExcelTemplateParamResponseDTO;
import com.wondernect.stars.office.excel.dto.param.ListExcelTemplateParamRequestDTO;
import com.wondernect.stars.office.excel.dto.template.ExcelTemplateResponseDTO;
import com.wondernect.stars.office.feign.excel.param.ExcelTemplateParamServerService;
import com.wondernect.stars.office.feign.excel.template.ExcelTemplateServerService;
import com.wondernect.stars.user.dto.ListUserRequestDTO;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserService
 * Author: chenxun
 * Date: 2020-06-25 19:33
 * Description:
 */
@Service
public class LocalUserExcelExportResponseService extends ESExcelExportResponseService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelExportResponseService.class);

    @Autowired
    private ExcelTemplateServerService excelTemplateServerService;

    @Autowired
    private ExcelTemplateParamServerService excelTemplateParamServerService;

    @Autowired
    private UserServerService userServerService;

    public void exportDataResponse(String templateId, ListUserRequestDTO listUserRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("模板信息不存在");
        }
        super.excelDataExport(templateId, LocalUserExcelDTO.class, userServerService.list(listUserRequestDTO), excelTemplateResponseDTO.getName(), excelTemplateResponseDTO.getName(), excelTemplateResponseDTO.getName(), request, response);
    }

    public void modelDataResponse(String templateId, HttpServletRequest request, HttpServletResponse response) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("模板信息不存在");
        }
        super.excelDataExport(templateId, LocalUserExcelDTO.class, new ArrayList<>(), excelTemplateResponseDTO.getName(), excelTemplateResponseDTO.getName(), excelTemplateResponseDTO.getName(), request, response);
    }

    @Override
    public List<ESExcelItemHandler> generateExcelItemHandlerList(String templateId) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("导入或导出模板不存在");
        }
        List<ExcelTemplateParamResponseDTO> excelTemplateParamResponseDTOList = excelTemplateParamServerService.list(new ListExcelTemplateParamRequestDTO(excelTemplateResponseDTO.getId(), null, null));
        if (CollectionUtils.isEmpty(excelTemplateParamResponseDTOList)) {
            throw new BusinessException("导入或导出模板配置参数为空");
        }
        List<ESExcelItemHandler> excelItemHandlerList = new ArrayList<>();
        for (ExcelTemplateParamResponseDTO excelTemplateParamResponseDTO : excelTemplateParamResponseDTOList) {
            switch (excelTemplateParamResponseDTO.getName()) {
                case "gender":
                {
                    excelItemHandlerList.add(
                            new LocalUserExcelGenderItemHandler(
                                    excelTemplateParamResponseDTO.getName(),
                                    excelTemplateParamResponseDTO.getTitle(),
                                    excelTemplateParamResponseDTO.getOrderNum()
                            )
                    );
                    break;
                }
                case "create_time":
                {
                    excelItemHandlerList.add(
                            new ESExcelTimestampItemHandler(
                                    excelTemplateParamResponseDTO.getName(),
                                    excelTemplateParamResponseDTO.getTitle(),
                                    excelTemplateParamResponseDTO.getOrderNum()
                            )
                    );
                    break;
                }
                default:
                {
                    if (ESStringUtils.equals(String.class.getName(), excelTemplateParamResponseDTO.getType())) {
                        excelItemHandlerList.add(
                                new ESExcelStringItemHandler(
                                        excelTemplateParamResponseDTO.getName(),
                                        excelTemplateParamResponseDTO.getTitle(),
                                        excelTemplateParamResponseDTO.getOrderNum()
                                )
                        );
                    } else if (ESStringUtils.equals(Boolean.class.getName(), excelTemplateParamResponseDTO.getType())) {
                        excelItemHandlerList.add(
                                new ESExcelBooleanItemHandler(
                                        excelTemplateParamResponseDTO.getName(),
                                        excelTemplateParamResponseDTO.getTitle(),
                                        excelTemplateParamResponseDTO.getOrderNum()
                                )
                        );
                    }
                }
            }
        }
        return excelItemHandlerList;
    }
}

package com.wondernect.services.ums.open.module.user.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelBooleanItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelStringItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelTimestampItemHandler;
import com.wondernect.elements.easyoffice.excel.model.ESExcelItem;
import com.wondernect.elements.easyoffice.excel.service.ESExcelImportResponseService;
import com.wondernect.elements.easyoffice.excel.util.ESExcelUtils;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelDTO;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelGenderItemHandler;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelImportDataHandler;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelImportVerifyHandler;
import com.wondernect.stars.office.excel.dto.param.ExcelTemplateParamResponseDTO;
import com.wondernect.stars.office.excel.dto.param.ListExcelTemplateParamRequestDTO;
import com.wondernect.stars.office.excel.dto.template.ExcelTemplateResponseDTO;
import com.wondernect.stars.office.feign.excel.param.ExcelTemplateParamServerService;
import com.wondernect.stars.office.feign.excel.template.ExcelTemplateServerService;
import com.wondernect.stars.user.dto.SaveLocalUserRequestDTO;
import com.wondernect.stars.user.em.Gender;
import com.wondernect.stars.user.em.UserType;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserService
 * Author: chenxun
 * Date: 2020-06-25 19:33
 * Description:
 */
@Service
public class LocalUserExcelImportResponseService extends ESExcelImportResponseService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelImportResponseService.class);

    @Autowired
    private ExcelTemplateServerService excelTemplateServerService;

    @Autowired
    private ExcelTemplateParamServerService excelTemplateParamServerService;

    @Autowired
    private LocalUserExcelImportDataHandler userImportDataHandler;

    @Autowired
    private LocalUserExcelImportVerifyHandler userImportVerifyHandler;

    @Autowired
    private UserServerService userServerService;

    public void importDataResponse(String templateId, InputStream fileInputStream, HttpServletRequest request, HttpServletResponse response) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("模板信息不存在");
        }
        super.excelDataImport(templateId, LocalUserExcelDTO.class, userImportDataHandler, userImportVerifyHandler, 1, 1, fileInputStream, excelTemplateResponseDTO.getName() + "错误信息", request, response);
    }

    @Override
    public void saveExcelImportEntityData(Map<String, Object> map, List<ESExcelItem> excelItemList) {
        LocalUserExcelDTO localUserExcelDTO = ESExcelUtils.getImportObject(LocalUserExcelDTO.class, map, excelItemList);
        if (ESObjectUtils.isNotNull(localUserExcelDTO)) {
            userServerService.create(
                    new SaveLocalUserRequestDTO(
                            null,
                            UserType.LOCAL,
                            localUserExcelDTO.getUsername(),
                            localUserExcelDTO.getName(),
                            ESObjectUtils.isNotNull(localUserExcelDTO.getGender()) ? localUserExcelDTO.getGender() : Gender.UNKNOWN,
                            localUserExcelDTO.getAvatar(),
                            localUserExcelDTO.getMobile(),
                            localUserExcelDTO.getEmail(),
                            localUserExcelDTO.getLocation(),
                            localUserExcelDTO.getRemark(),
                            localUserExcelDTO.getRoleTypeId(),
                            localUserExcelDTO.getRoleId(),
                            localUserExcelDTO.getPassword(),
                            ESObjectUtils.isNotNull(localUserExcelDTO.getEnable()) ? localUserExcelDTO.getEnable() : true,
                            ESObjectUtils.isNotNull(localUserExcelDTO.getEditable()) ? localUserExcelDTO.getEditable() : true,
                            ESObjectUtils.isNotNull(localUserExcelDTO.getDeletable()) ? localUserExcelDTO.getDeletable() : true
                    )
            );
        }
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

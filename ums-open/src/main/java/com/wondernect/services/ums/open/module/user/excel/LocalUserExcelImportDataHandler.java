package com.wondernect.services.ums.open.module.user.excel;

import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelImportDataHandler;
import com.wondernect.stars.office.excel.dto.bean.ExcelBeanResponseDTO;
import com.wondernect.stars.office.excel.dto.property.ExcelBeanPropertyResponseDTO;
import com.wondernect.stars.office.excel.dto.property.ListExcelBeanPropertyRequestDTO;
import com.wondernect.stars.office.feign.excel.bean.ExcelBeanServerService;
import com.wondernect.stars.office.feign.excel.property.ExcelBeanPropertyServerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserExportHandler
 * Author: chenxun
 * Date: 2020-11-12 15:15
 * Description: 用户导入handler
 */
@Service
public class LocalUserExcelImportDataHandler extends ESExcelImportDataHandler {

    @Autowired
    private ExcelBeanServerService excelBeanServerService;

    @Autowired
    private ExcelBeanPropertyServerService excelBeanPropertyServerService;

    public Map<String, String> getDataPropertyMapping() {
        Map<String, String> dictionary = new HashMap<>();
        ExcelBeanResponseDTO excelBeanResponseDTO = excelBeanServerService.detailByBean(LocalUserExcelDTO.class.getName());
        if (ESObjectUtils.isNotNull(excelBeanResponseDTO)) {
            List<ExcelBeanPropertyResponseDTO> excelBeanPropertyResponseDTOList = excelBeanPropertyServerService.list(new ListExcelBeanPropertyRequestDTO(excelBeanResponseDTO.getId(), null));
            if (CollectionUtils.isNotEmpty(excelBeanPropertyResponseDTOList)) {
                for (ExcelBeanPropertyResponseDTO excelBeanPropertyResponseDTO : excelBeanPropertyResponseDTOList) {
                    dictionary.put(excelBeanPropertyResponseDTO.getTitle(), excelBeanPropertyResponseDTO.getName());
                }
            }
        }
        return dictionary;
    }
}

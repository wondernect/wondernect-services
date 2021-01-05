package com.wondernect.services.ums.open.module.user.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.easyoffice.excel.model.ESExcelItem;
import com.wondernect.elements.easyoffice.excel.util.ESExcelUtils;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelDTO;
import com.wondernect.stars.office.excel.dto.bean.ExcelBeanResponseDTO;
import com.wondernect.stars.office.excel.dto.bean.SaveExcelBeanRequestDTO;
import com.wondernect.stars.office.excel.dto.property.ExcelBeanPropertyResponseDTO;
import com.wondernect.stars.office.excel.dto.property.SaveExcelBeanPropertyRequestDTO;
import com.wondernect.stars.office.feign.excel.bean.ExcelBeanServerService;
import com.wondernect.stars.office.feign.excel.property.ExcelBeanPropertyServerService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserService
 * Author: chenxun
 * Date: 2020-06-25 19:33
 * Description:
 */
@Service
public class LocalUserExcelInitService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelInitService.class);

    @Autowired
    private ExcelBeanServerService excelBeanServerService;

    @Autowired
    private ExcelBeanPropertyServerService excelBeanPropertyServerService;

    public void initExcelItem(boolean forceUpdate) {
        List<ESExcelItem> excelItemList = ESExcelUtils.getAllEntityExcelItem(LocalUserExcelDTO.class);
        if (CollectionUtils.isNotEmpty(excelItemList)) {
            ESExcelItem excelItemOne = excelItemList.get(0);
            ExcelBeanResponseDTO excelBeanResponseDTO = excelBeanServerService.detailByBean(excelItemOne.getEntity());
            if (ESObjectUtils.isNull(excelBeanResponseDTO)) {
                excelBeanResponseDTO = excelBeanServerService.create(
                        new SaveExcelBeanRequestDTO(
                                excelItemOne.getEntity(),
                                excelItemOne.getEntityName()
                        )
                );
            } else {
                if (!forceUpdate) {
                    throw new BusinessException("指定实体对象已完成初始化");
                }
                excelBeanResponseDTO = excelBeanServerService.update(
                        excelBeanResponseDTO.getId(),
                        new SaveExcelBeanRequestDTO(
                                excelItemOne.getEntity(),
                                excelItemOne.getEntityName()
                        )
                );
            }
            for (ESExcelItem excelItem : excelItemList) {
                ExcelBeanPropertyResponseDTO excelBeanPropertyResponseDTO = excelBeanPropertyServerService.detailByBeanIdAndName(excelBeanResponseDTO.getId(), excelItem.getName());
                if (ESObjectUtils.isNull(excelBeanPropertyResponseDTO)) {
                    excelBeanPropertyServerService.create(
                            new SaveExcelBeanPropertyRequestDTO(
                                    excelBeanResponseDTO.getId(),
                                    excelItem.getName(),
                                    excelItem.getType(),
                                    excelItem.getTitle(),
                                    excelItem.getOrderNum(),
                                    excelItem.getGetMethodName(),
                                    excelItem.getSetMethodName()
                            )
                    );
                } else {
                    excelBeanPropertyServerService.update(
                            excelBeanPropertyResponseDTO.getId(),
                            new SaveExcelBeanPropertyRequestDTO(
                                    excelBeanPropertyResponseDTO.getBeanId(),
                                    excelBeanPropertyResponseDTO.getName(),
                                    excelItem.getType(),
                                    excelItem.getTitle(),
                                    excelItem.getOrderNum(),
                                    excelItem.getGetMethodName(),
                                    excelItem.getSetMethodName()
                            )
                    );
                }
            }
        }
    }
}

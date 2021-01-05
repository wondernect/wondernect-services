package com.wondernect.services.ums.open.module.user.service;

import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESDateTimeUtils;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelBooleanItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelStringItemHandler;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelTimestampItemHandler;
import com.wondernect.elements.easyoffice.excel.service.ESExcelExportFileService;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelDTO;
import com.wondernect.services.ums.open.module.user.excel.LocalUserExcelGenderItemHandler;
import com.wondernect.stars.file.dto.FileResponseDTO;
import com.wondernect.stars.file.dto.LocalFilePathResponseDTO;
import com.wondernect.stars.file.em.FileType;
import com.wondernect.stars.file.feign.local.LocalFileFeignClient;
import com.wondernect.stars.file.feign.path.LocalFilePathServerService;
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
import org.springframework.web.multipart.MultipartFile;

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
public class LocalUserExcelExportFileService extends ESExcelExportFileService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelExportFileService.class);

    @Autowired
    private ExcelTemplateServerService excelTemplateServerService;

    @Autowired
    private ExcelTemplateParamServerService excelTemplateParamServerService;

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private LocalFileFeignClient localFileFeignClient;

    @Autowired
    private LocalFilePathServerService localFilePathServerService;

    public BusinessData<FileResponseDTO> exportDataFile(String templateId, ListUserRequestDTO listUserRequestDTO, String localFilePathId) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("模板信息不存在");
        }
        LocalFilePathResponseDTO localFilePathResponseDTO;
        if (ESStringUtils.isBlank(localFilePathId)) {
            localFilePathResponseDTO = localFilePathServerService.root();
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("当前应用没有根节点文件存储路径,请先创建");
            }
        } else {
            localFilePathResponseDTO = localFilePathServerService.detail(localFilePathId);
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("文件存储路径不存在");
            }
        }
        if (ESStringUtils.isBlank(localFilePathResponseDTO.getSubFilePath())) {
            throw new BusinessException("文件存储路径为空");
        }
        String fileName = excelTemplateResponseDTO.getName() + ESDateTimeUtils.formatDate(ESDateTimeUtils.getCurrentTimestamp(), "-yyyy-MM-dd-HH-SS") + ".xlsx";
        MultipartFile multipartFile = super.excelDataExport(
                templateId,
                LocalUserExcelDTO.class,
                userServerService.list(listUserRequestDTO),
                excelTemplateResponseDTO.getName(),
                excelTemplateResponseDTO.getName(),
                fileName
        );
        if (ESObjectUtils.isNull(multipartFile)) {
            return new BusinessData<>(BusinessError.SUCCESS);
        }
        return localFileFeignClient.upload(FileType.FILE.name(), localFilePathResponseDTO.getId(), multipartFile);
    }

    public BusinessData<FileResponseDTO> modelDataFile(String templateId, String localFilePathId) {
        ExcelTemplateResponseDTO excelTemplateResponseDTO = excelTemplateServerService.detail(templateId);
        if (ESObjectUtils.isNull(excelTemplateResponseDTO)) {
            throw new BusinessException("模板信息不存在");
        }
        LocalFilePathResponseDTO localFilePathResponseDTO;
        if (ESStringUtils.isBlank(localFilePathId)) {
            localFilePathResponseDTO = localFilePathServerService.root();
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("当前应用没有根节点文件存储路径,请先创建");
            }
        } else {
            localFilePathResponseDTO = localFilePathServerService.detail(localFilePathId);
            if (ESObjectUtils.isNull(localFilePathResponseDTO)) {
                throw new BusinessException("文件存储路径不存在");
            }
        }
        if (ESStringUtils.isBlank(localFilePathResponseDTO.getSubFilePath())) {
            throw new BusinessException("文件存储路径为空");
        }
        String fileName = excelTemplateResponseDTO.getName() + ESDateTimeUtils.formatDate(ESDateTimeUtils.getCurrentTimestamp(), "-yyyy-MM-dd-HH-SS") + ".xlsx";
        MultipartFile multipartFile = super.excelDataExport(
                templateId,
                LocalUserExcelDTO.class,
                new ArrayList<>(),
                excelTemplateResponseDTO.getName(),
                excelTemplateResponseDTO.getName(),
                fileName
        );
        if (ESObjectUtils.isNull(multipartFile)) {
            return new BusinessData<>(BusinessError.SUCCESS);
        }
        return localFileFeignClient.upload(FileType.FILE.name(), localFilePathResponseDTO.getId(), multipartFile);
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

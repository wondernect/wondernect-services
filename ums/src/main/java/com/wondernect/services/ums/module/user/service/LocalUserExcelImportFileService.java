package com.wondernect.services.ums.module.user.service;

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
import com.wondernect.elements.easyoffice.excel.model.ESExcelItem;
import com.wondernect.elements.easyoffice.excel.service.ESExcelImportFileService;
import com.wondernect.elements.easyoffice.excel.util.ESExcelUtils;
import com.wondernect.services.ums.module.user.excel.LocalUserExcelDTO;
import com.wondernect.services.ums.module.user.excel.LocalUserExcelGenderItemHandler;
import com.wondernect.services.ums.module.user.excel.LocalUserExcelImportDataHandler;
import com.wondernect.services.ums.module.user.excel.LocalUserExcelImportVerifyHandler;
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
import com.wondernect.stars.user.dto.SaveLocalUserRequestDTO;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.em.Gender;
import com.wondernect.stars.user.em.UserType;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class LocalUserExcelImportFileService extends ESExcelImportFileService {

    private static final Logger logger = LoggerFactory.getLogger(LocalUserExcelImportFileService.class);

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

    @Autowired
    private LocalFileFeignClient localFileFeignClient;

    @Autowired
    private LocalFilePathServerService localFilePathServerService;

    public BusinessData<FileResponseDTO> importDataFile(String templateId, InputStream fileInputStream, String localFilePathId) {
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
        String fileName = excelTemplateResponseDTO.getName() + ESDateTimeUtils.formatDate(ESDateTimeUtils.getCurrentTimestamp(), "-yyyy-MM-dd-HH-SS") + "错误信息.xlsx";
        MultipartFile multipartFile = super.excelDataImport(
                templateId,
                LocalUserExcelDTO.class,
                userImportDataHandler,
                userImportVerifyHandler,
                1,
                1,
                fileInputStream,
                fileName
        );
        if (ESObjectUtils.isNull(multipartFile)) {
            return new BusinessData<>(BusinessError.SUCCESS);
        }
        return localFileFeignClient.upload(FileType.FILE.name(), localFilePathResponseDTO.getId(), multipartFile);
    }

    @Override
    public void saveExcelImportEntityData(Map<String, Object> map, List<ESExcelItem> excelItemList) {
        LocalUserExcelDTO localUserExcelDTO = ESExcelUtils.getImportObject(LocalUserExcelDTO.class, map, excelItemList);
        if (ESObjectUtils.isNotNull(localUserExcelDTO)) {
            UserResponseDTO userResponseDTO = userServerService.detailByUsername(localUserExcelDTO.getUsername());
            if (ESObjectUtils.isNotNull(userResponseDTO)) {
                userServerService.update(
                        userResponseDTO.getId(),
                        new SaveLocalUserRequestDTO(
                                null,
                                UserType.LOCAL,
                                localUserExcelDTO.getUsername(),
                                ESStringUtils.isBlank(localUserExcelDTO.getName()) ? userResponseDTO.getName() : localUserExcelDTO.getName(),
                                ESObjectUtils.isNotNull(localUserExcelDTO.getGender()) ? localUserExcelDTO.getGender() : Gender.UNKNOWN,
                                ESStringUtils.isBlank(localUserExcelDTO.getAvatar()) ? userResponseDTO.getAvatar() : localUserExcelDTO.getAvatar(),
                                ESStringUtils.isBlank(localUserExcelDTO.getMobile()) ? userResponseDTO.getMobile() : localUserExcelDTO.getMobile(),
                                ESStringUtils.isBlank(localUserExcelDTO.getEmail()) ? userResponseDTO.getEmail() : localUserExcelDTO.getEmail(),
                                ESStringUtils.isBlank(localUserExcelDTO.getLocation()) ? userResponseDTO.getLocation() : localUserExcelDTO.getLocation(),
                                ESStringUtils.isBlank(localUserExcelDTO.getRemark()) ? userResponseDTO.getRemark() : localUserExcelDTO.getRemark(),
                                ESStringUtils.isBlank(localUserExcelDTO.getRoleTypeId()) ? userResponseDTO.getRoleTypeId() : localUserExcelDTO.getRoleTypeId(),
                                ESStringUtils.isBlank(localUserExcelDTO.getRoleId()) ? userResponseDTO.getRoleId() : localUserExcelDTO.getRoleId(),
                                localUserExcelDTO.getPassword(),
                                ESObjectUtils.isNotNull(localUserExcelDTO.getEnable()) ? localUserExcelDTO.getEnable() : true,
                                ESObjectUtils.isNotNull(localUserExcelDTO.getEditable()) ? localUserExcelDTO.getEditable() : true,
                                ESObjectUtils.isNotNull(localUserExcelDTO.getDeletable()) ? localUserExcelDTO.getDeletable() : true
                        )
                );
            } else {
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

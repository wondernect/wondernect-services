package com.wondernect.services.ums.stars.module.file.service;

import com.wondernect.elements.authorize.context.WondernectCommonContext;
import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.services.ums.stars.config.UmsStarsConfigProperties;
import com.wondernect.stars.file.model.LocalFilePath;
import com.wondernect.stars.file.service.localfilepath.LocalFilePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: FileInitService
 * Author: chenxun
 * Date: 2020-06-27 02:25
 * Description:
 */
@Service
public class FileInitService implements ApplicationListener<WondernectBootEvent> {

    @Autowired
    private UmsStarsConfigProperties umsStarsConfigProperties;

    @Autowired
    private LocalFilePathService localFilePathService;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                // 初始化根节点
                if (ESObjectUtils.isNull(localFilePathService.findEntityById(umsStarsConfigProperties.getRootFilePathId()))) {
                    LocalFilePath localFilePath = new LocalFilePath(
                            "文件存储根节点",
                            "文件存储根节点",
                            "",
                            "",
                            "-1",
                            false
                    );
                    localFilePath.setId(umsStarsConfigProperties.getRootFilePathId());
                    localFilePathService.save(localFilePath);
                }
                break;
            }
            default:
                break;
        }
    }
}

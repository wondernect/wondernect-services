package com.wondernect.services.ums.file;

import com.wondernect.elements.boot.application.event.WondernectBootEvent;
import com.wondernect.elements.common.utils.ESObjectUtils;
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
    private LocalFilePathService localFilePathService;

    @Override
    public void onApplicationEvent(WondernectBootEvent wondernectBootEvent) {
        switch (wondernectBootEvent.getWondernectBootEventType()) {
            case BOOT:
            {
                // 初始化根节点
                if (ESObjectUtils.isNull(localFilePathService.findEntityById("ROOT_FILE_PATH"))) {
                    LocalFilePath localFilePath = new LocalFilePath(
                            "文件存储根节点",
                            "文件存储根节点",
                            "",
                            "",
                            "-1"
                    );
                    localFilePath.setId("ROOT_FILE_PATH");
                    localFilePathService.save(localFilePath);
                }
                // 初始化UMS根节点
                if (ESObjectUtils.isNull(localFilePathService.findEntityById("UMS_FILE_PATH"))) {
                    LocalFilePath localFilePath = new LocalFilePath(
                            "UMS文件存储根节点",
                            "UMS文件存储根节点",
                            "ums",
                            "ums",
                            "-1"
                    );
                    localFilePath.setId("UMS_FILE_PATH");
                    localFilePath.setCreateApp("UMS");
                    localFilePathService.save(localFilePath);
                }
                break;
            }
            default:
                break;
        }
    }
}

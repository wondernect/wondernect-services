package com.wondernect.services.ums.rbac.log;

import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.logger.request.AbstractRequestLoggerRecordService;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2020, wondernect.com
 * FileName: UMSAppRequestLogService
 * Author: chenxun
 * Date: 2020/12/9 14:48
 * Description:
 */
@Service
public class UMSRBACRequestLogService extends AbstractRequestLoggerRecordService {

    private static final String UMS_RBAC_DEFAULT_LOG_LEVEL = "INFO";

    private static final String UMS_RBAC_SERVICE = "ums-rbac";

    @Override
    public void recordRequestLog(String level, String service, String module, String userId, String appId, String operation, String description, String requestId, String url, String method, String argValue, String returnValue, Long runStartTime, Long runTime, String ip, String devicePlatform, String deviceDescription) {
        if (ESStringUtils.isBlank(level)) {
            level = UMS_RBAC_DEFAULT_LOG_LEVEL;
        }
        if (ESStringUtils.isBlank(service)) {
            service = UMS_RBAC_SERVICE;
        }
        super.recordRequestLog(level, service, module, userId, appId, operation, description, requestId, url, method, argValue, returnValue, runStartTime, runTime, ip, devicePlatform, deviceDescription);
    }
}

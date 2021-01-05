package com.wondernect.services.ums.open.log;

import com.wondernect.elements.logger.request.AbstractRequestLoggerRecordService;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2020, wondernect.com
 * FileName: UMSRequestLogService
 * Author: chenxun
 * Date: 2020/12/9 14:48
 * Description:
 */
@Service
public class UMSOpenRequestLogService extends AbstractRequestLoggerRecordService {

    @Override
    public String defaultRequestLogLevel() {
        return "INFO";
    }

    @Override
    public String defaultExceptionRequestLogLevel() {
        return "ERROR";
    }

    @Override
    public String defaultService() {
        return "ums-open";
    }

    @Override
    public void doRecordRequestLog(String level, String service, String module, String userId, String appId, String operation, String description, String requestId, String url, String method, String argValue, String returnValue, Long runStartTime, Long runTime, String ip, String devicePlatform, String deviceDescription) {

    }
}

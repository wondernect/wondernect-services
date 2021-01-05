package com.wondernect.services.ums.stars.module.database.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.database.dto.DatabaseModifyPasswordRequestDTO;
import com.wondernect.stars.database.dto.DatabaseUserManageResponseDTO;
import com.wondernect.stars.database.model.DatabaseRootManage;
import com.wondernect.stars.database.model.DatabaseUserManage;
import com.wondernect.stars.database.service.databaseRootManage.DatabaseRootManageService;
import com.wondernect.stars.database.service.databaseUserManage.DatabaseUserManageContext;
import com.wondernect.stars.database.service.databaseUserManage.DatabaseUserManageService;
import com.wondernect.stars.database.service.databaseUserManage.MysqlDatabaseUserManageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserManageClientService {

    @Autowired
    private DatabaseRootManageService databaseRootManageService;

    @Autowired
    private DatabaseUserManageService databaseUserManageService;

    @Autowired
    private MysqlDatabaseUserManageImpl mysqlDatabaseUserManageImpl;

    public DatabaseUserManageResponseDTO modifyPassword(DatabaseModifyPasswordRequestDTO databaseModifyPasswordRequestDTO) {
        DatabaseUserManage databaseUserManage = databaseUserManageService.findEntityById(databaseModifyPasswordRequestDTO.getDatabaseUserId());
        if (ESObjectUtils.isNull(databaseUserManage)) {
            throw new BusinessException("需要修改密码的用户不存在");
        }
        DatabaseRootManage databaseRootManage = databaseRootManageService.findEntityById(databaseUserManage.getDatabaseRootManageId());
        if (ESObjectUtils.isNull(databaseRootManage)) {
            throw new BusinessException("要修改密码的用户不存在数据库管理服务");
        }
        switch (databaseRootManage.getType()) {
            case MySQL: {
                DatabaseUserManageContext databaseUserManageContext = new DatabaseUserManageContext(mysqlDatabaseUserManageImpl);
                return databaseUserManageContext.modifyPassword(databaseModifyPasswordRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }
}

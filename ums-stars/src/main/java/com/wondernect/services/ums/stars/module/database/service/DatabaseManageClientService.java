package com.wondernect.services.ums.stars.module.database.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.database.dto.DatabaseManageResponseDTO;
import com.wondernect.stars.database.dto.SaveDatabaseManageRequestDTO;
import com.wondernect.stars.database.model.DatabaseManage;
import com.wondernect.stars.database.model.DatabaseRootManage;
import com.wondernect.stars.database.service.databaseManage.DatabaseManageContext;
import com.wondernect.stars.database.service.databaseManage.DatabaseManageService;
import com.wondernect.stars.database.service.databaseManage.MysqlDatabaseManageImpl;
import com.wondernect.stars.database.service.databaseRootManage.DatabaseRootManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseManageClientService {

    @Autowired
    private DatabaseRootManageService databaseRootManageService;

    @Autowired
    private DatabaseManageService databaseManageService;

    @Autowired
    private MysqlDatabaseManageImpl mysqlDatabaseManageImpl;

    public DatabaseManageResponseDTO create(SaveDatabaseManageRequestDTO saveDatabaseManageRequestDTO) {
        DatabaseRootManage databaseRootManage = databaseRootManageService.findEntityById(saveDatabaseManageRequestDTO.getDatabaseRootManageId());
        if (ESObjectUtils.isNull(databaseRootManage)) {
            throw new BusinessException("数据库管理服务不存在");
        }
        switch (databaseRootManage.getType()) {
            case MySQL: {
                DatabaseManageContext databaseManageContext = new DatabaseManageContext(mysqlDatabaseManageImpl);
                return databaseManageContext.create(saveDatabaseManageRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

    public DatabaseManageResponseDTO update(String id, SaveDatabaseManageRequestDTO saveDatabaseManageRequestDTO) {
        DatabaseRootManage databaseRootManage = databaseRootManageService.findEntityById(saveDatabaseManageRequestDTO.getDatabaseRootManageId());
        if (ESObjectUtils.isNull(databaseRootManage)) {
            throw new BusinessException("数据库管理服务不存在");
        }
        switch (databaseRootManage.getType()) {
            case MySQL: {
                DatabaseManageContext databaseManageContext = new DatabaseManageContext(mysqlDatabaseManageImpl);
                return databaseManageContext.update(id, saveDatabaseManageRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

    public DatabaseManageResponseDTO initDatabase(String id) {
        DatabaseManage databaseManage = databaseManageService.findEntityById(id);
        if (ESObjectUtils.isNull(databaseManage)) {
            throw new BusinessException("要初始化的数据库不存在");
        }
        switch (databaseManage.getType()) {
            case "MySQL": {
                DatabaseManageContext databaseManageContext = new DatabaseManageContext(mysqlDatabaseManageImpl);
                return databaseManageContext.initDatabase(id);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }
}

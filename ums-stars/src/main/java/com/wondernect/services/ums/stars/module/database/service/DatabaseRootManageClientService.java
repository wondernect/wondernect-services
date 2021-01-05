package com.wondernect.services.ums.stars.module.database.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.stars.database.dto.DatabaseRootManageResponseDTO;
import com.wondernect.stars.database.dto.SaveDatabaseRootManageRequestDTO;
import com.wondernect.stars.database.service.databaseRootManage.DatabaseRootManageContext;
import com.wondernect.stars.database.service.databaseRootManage.MysqlDatabaseRootManageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRootManageClientService {

    @Autowired
    private MysqlDatabaseRootManageImpl mysqlDatabaseRootManageImpl;

    public DatabaseRootManageResponseDTO create(SaveDatabaseRootManageRequestDTO saveDatabaseRootManageRequestDTO) {
        switch (saveDatabaseRootManageRequestDTO.getType()) {
            case MySQL: {
                DatabaseRootManageContext databaseRootManageContext = new DatabaseRootManageContext(mysqlDatabaseRootManageImpl);
                return databaseRootManageContext.create(saveDatabaseRootManageRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

    public DatabaseRootManageResponseDTO update(String id, SaveDatabaseRootManageRequestDTO saveDatabaseRootManageRequestDTO) {
        switch (saveDatabaseRootManageRequestDTO.getType()) {
            case MySQL: {
                DatabaseRootManageContext databaseRootManageContext = new DatabaseRootManageContext(mysqlDatabaseRootManageImpl);
                return databaseRootManageContext.update(id, saveDatabaseRootManageRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }
}

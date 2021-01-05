package com.wondernect.services.ums.stars.module.database.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.database.dto.DatabaseUserRequestDTO;
import com.wondernect.stars.database.dto.DatabaseUserRightsShipResponseDTO;
import com.wondernect.stars.database.dto.SaveDatabaseUserRightsShipRequestDTO;
import com.wondernect.stars.database.dto.TestConnectResponseDTO;
import com.wondernect.stars.database.model.DatabaseManage;
import com.wondernect.stars.database.service.databaseManage.DatabaseManageService;
import com.wondernect.stars.database.service.databaseUserRightsShip.DatabaseUserRightsShipContext;
import com.wondernect.stars.database.service.databaseUserRightsShip.MysqlDatabaseUserRightsShipImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserRightsShipClientService {

    @Autowired
    private DatabaseManageService databaseManageService;

    @Autowired
    private MysqlDatabaseUserRightsShipImpl mysqlDatabaseUserRightsShipImpl;

    //赋权限
    public DatabaseUserRightsShipResponseDTO giveRights(int type, SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO) {
        DatabaseManage databaseManage = databaseManageService.findEntityById(saveDatabaseUserRightsShipRequestDTO.getDatabaseManageId());
        if (ESObjectUtils.isNull(databaseManage)) {
            throw new BusinessException("要赋权限的数据库不存在");
        }
        switch (databaseManage.getType()) {
            case "MySQL": {
                DatabaseUserRightsShipContext databaseUserRightsShipContext = new DatabaseUserRightsShipContext(mysqlDatabaseUserRightsShipImpl);
                return databaseUserRightsShipContext.giveRights(type, saveDatabaseUserRightsShipRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

    public void revokeRights(SaveDatabaseUserRightsShipRequestDTO saveDatabaseUserRightsShipRequestDTO) {
        DatabaseManage databaseManage = databaseManageService.findEntityById(saveDatabaseUserRightsShipRequestDTO.getDatabaseManageId());
        if (ESObjectUtils.isNull(databaseManage)) {
            throw new BusinessException("要收回权限的数据库不存在");
        }
        switch (databaseManage.getType()) {
            case "MySQL": {
                DatabaseUserRightsShipContext databaseUserRightsShipContext = new DatabaseUserRightsShipContext(mysqlDatabaseUserRightsShipImpl);
                databaseUserRightsShipContext.revokeRights(saveDatabaseUserRightsShipRequestDTO);
                return;
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

    public TestConnectResponseDTO testConnect(DatabaseUserRequestDTO databaseUserRequestDTO) {
        DatabaseManage databaseManage = databaseManageService.findEntityById(databaseUserRequestDTO.getDatabaseManageId());
        if (ESObjectUtils.isNull(databaseManage)) {
            throw new BusinessException("要测试连接的数据库不存在");
        }
        switch (databaseManage.getType()) {
            case "MySQL": {
                DatabaseUserRightsShipContext databaseUserRightsShipContext = new DatabaseUserRightsShipContext(mysqlDatabaseUserRightsShipImpl);
                return databaseUserRightsShipContext.testConnect(databaseUserRequestDTO);
            }
            default: {
                throw new BusinessException("所选的数据库类型暂不支持");
            }
        }
    }

}

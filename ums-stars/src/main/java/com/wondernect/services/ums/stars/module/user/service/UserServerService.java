package com.wondernect.services.ums.stars.module.user.service;

import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.rbac.model.Role;
import com.wondernect.stars.rbac.model.RoleType;
import com.wondernect.stars.rbac.service.role.RoleService;
import com.wondernect.stars.rbac.service.roletype.RoleTypeService;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.model.User;
import com.wondernect.stars.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2017-2020, wondernect.com
 * FileName: UserServerService
 * Author: chenxun
 * Date: 2020/11/25 11:50
 * Description:
 */
@Service
public class UserServerService extends UserService {

    @Autowired
    private RoleTypeService roleTypeService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserResponseDTO generate(User user) {
        UserResponseDTO userResponseDTO = super.generate(user);
        RoleType roleType = roleTypeService.findEntityById(user.getRoleTypeId());
        userResponseDTO.setRoleTypeName(ESObjectUtils.isNotNull(roleType) ? roleType.getName() : null);
        Role role = roleService.findEntityById(user.getRoleId());
        userResponseDTO.setRoleName(ESObjectUtils.isNotNull(role) ? role.getName() : null);
        return userResponseDTO;
    }
}

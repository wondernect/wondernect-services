package com.wondernect.services.ums.user.excel;

import com.wondernect.elements.common.error.BusinessError;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.common.utils.ESRegexUtils;
import com.wondernect.elements.common.utils.ESStringUtils;
import com.wondernect.elements.easyoffice.excel.handler.ESExcelImportVerifyHandler;
import com.wondernect.stars.rbac.feign.role.RoleServerService;
import com.wondernect.stars.rbac.feign.roleType.RoleTypeServerService;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserVerifyHandler
 * Author: chenxun
 * Date: 2020-11-12 15:19
 * Description:
 */
@Service
public class LocalUserExcelImportVerifyHandler extends ESExcelImportVerifyHandler {

    private int min = 6;

    private String passwordRegexPattern = "^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?![^a-zA-Z0-9]*$)\\S+$";

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private RoleTypeServerService roleTypeServerService;

    @Autowired
    private RoleServerService roleServerService;

    @Override
    public BusinessData verifyData(Map<String, Object> object) {

        Object username = object.get("username");
        if (ESObjectUtils.isNull(username)) {
            return new BusinessData("用户登录名不能为空");
        }

        Object password = object.get("password");
        if (ESObjectUtils.isNotNull(password)) {
            if (password.toString().length() < min || !ESRegexUtils.match(passwordRegexPattern, password.toString())) {
                return new BusinessData("密码强度不符合规则要求(至少6位，数字、大小写字母、特殊字符包含两种或两种以上)");
            }
        } else {
            return new BusinessData("密码不能为空");
        }

        Object name = object.get("name");
        if (ESObjectUtils.isNull(name)) {
            return new BusinessData("姓名不能为空");
        }

        Object gender = object.get("gender");
        if (ESObjectUtils.isNotNull(gender)) {
            if (!ESStringUtils.equals("男", gender.toString()) && !ESStringUtils.equals("女", gender.toString()) && !ESStringUtils.equals("未知", gender.toString())) {
                return new BusinessData("性别有误");
            }
        }

        Object mobile = object.get("mobile");
        if (ESObjectUtils.isNotNull(mobile)) {
            if (!ESRegexUtils.isMobile(mobile.toString())) {
                return new BusinessData("手机号码格式有误");
            }
        }

        Object email = object.get("email");
        if (ESObjectUtils.isNotNull(email)) {
            if (!ESRegexUtils.isEmail(email.toString())) {
                return new BusinessData("邮箱格式有误");
            }
        }

        Object roleTypeId = object.get("role_type_id");
        if (ESObjectUtils.isNotNull(roleTypeId)) {
            if (ESObjectUtils.isNull(roleTypeServerService.detail(roleTypeId.toString()))) {
                return new BusinessData("角色类型id不存在");
            }
        } else {
            return new BusinessData("角色类型id不能为空");
        }

        Object roleId = object.get("role_id");
        if (ESObjectUtils.isNotNull(roleId)) {
            if (ESObjectUtils.isNull(roleServerService.detail(roleId.toString()))) {
                return new BusinessData("角色id不存在");
            }
        } else {
            return new BusinessData("角色id不能为空");
        }

        Object enable = object.get("enable");
        if (ESObjectUtils.isNotNull(enable)) {
            if (!ESStringUtils.equals("是", enable.toString()) && !ESStringUtils.equals("否", enable.toString())) {
                return new BusinessData("是否可用有误");
            }
        }

        Object editable = object.get("editable");
        if (ESObjectUtils.isNotNull(editable)) {
            if (!ESStringUtils.equals("是", editable.toString()) && !ESStringUtils.equals("否", editable.toString())) {
                return new BusinessData("是否可编辑有误");
            }
        }

        Object deletable = object.get("deletable");
        if (ESObjectUtils.isNotNull(deletable)) {
            if (!ESStringUtils.equals("是", deletable.toString()) && !ESStringUtils.equals("否", deletable.toString())) {
                return new BusinessData("是否可删除有误");
            }
        }

        return new BusinessData(BusinessError.SUCCESS);
    }
}

package com.wondernect.services.ums.module.user.excel;

import com.wondernect.elements.easyoffice.excel.handler.ESExcelEnumItemHandler;
import com.wondernect.stars.user.em.Gender;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别导入导出item handler
 *
 * @author chenxun 2020-11-20 15:42:20
 **/
public class LocalUserExcelGenderItemHandler extends ESExcelEnumItemHandler<Gender> {

    private static final Map<Gender, String> gender = new HashMap<>();

    static {
        gender.put(Gender.MALE, "男");
        gender.put(Gender.FEMALE, "女");
        gender.put(Gender.UNKNOWN, "未知");
    }

    public LocalUserExcelGenderItemHandler(String itemName, String itemTitle, int itemOrder) {
        super(itemName, itemTitle, itemOrder, gender);
    }
}
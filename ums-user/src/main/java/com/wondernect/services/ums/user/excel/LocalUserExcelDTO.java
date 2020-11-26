package com.wondernect.services.ums.user.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondernect.elements.rdb.response.BaseStringResponseDTO;
import com.wondernect.stars.user.em.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: UserExcelDTO
 * Author: chenxun
 * Date: 2020-11-22 21:20
 * Description: 本地用户导入导出对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "本地用户导入导出对象")
public class LocalUserExcelDTO extends BaseStringResponseDTO {

    @JsonProperty("username")
    @ApiModelProperty(notes = "用户登录名")
    private String username;

    @JsonProperty("password")
    @ApiModelProperty(notes = "密码")
    private String password;

    @JsonProperty("name")
    @ApiModelProperty(notes = "姓名")
    private String name;

    @JsonProperty("gender")
    @ApiModelProperty(notes = "性别")
    private Gender gender;

    @JsonProperty("avatar")
    @ApiModelProperty(notes = "头像")
    private String avatar;

    @JsonProperty("mobile")
    @ApiModelProperty(notes = "手机")
    private String mobile;

    @JsonProperty("email")
    @ApiModelProperty(notes = "邮箱")
    private String email;

    @JsonProperty("location")
    @ApiModelProperty(notes = "地理位置")
    private String location;

    @JsonProperty("remark")
    @ApiModelProperty(notes = "个性签名")
    private String remark;

    @JsonProperty("role_type_id")
    @ApiModelProperty(notes = "角色类型id")
    private String roleTypeId;

    @JsonProperty("role_type_name")
    @ApiModelProperty(notes = "角色类型名称")
    private String roleTypeName;

    @JsonProperty("role_id")
    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @JsonProperty("role_name")
    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    @JsonProperty("enable")
    @ApiModelProperty(notes = "是否可用")
    private Boolean enable;

    @JsonProperty("editable")
    @ApiModelProperty(notes = "是否可编辑")
    private Boolean editable;

    @JsonProperty("deletable")
    @ApiModelProperty(notes = "是否可删除")
    private Boolean deletable;
}

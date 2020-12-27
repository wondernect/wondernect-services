package com.wondernect.services.ums.module.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Copyright (C), 2017-2019, wondernect.com
 * FileName: LoginRequestDTO
 * Author: chenxun
 * Date: 2019/6/3 14:02
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录请求对象")
public class LoginRequestDTO {

    @NotBlank(message = "用户登录名不能为空")
    @JsonProperty("username")
    @ApiModelProperty(notes = "用户登录名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonProperty("password")
    @ApiModelProperty(notes = "密码")
    private String password;
}

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
@ApiModel(value = "应用授权请求对象")
public class AuthorizeRequestDTO {

    @NotBlank(message = "应用密钥绑定用户id不能为空")
    @JsonProperty("user_id")
    @ApiModelProperty(notes = "应用密钥绑定用户id")
    private String userId;

    @NotBlank(message = "应用id不能为空")
    @JsonProperty("app_id")
    @ApiModelProperty(notes = "应用id")
    private String appId;

    @NotBlank(message = "应用密钥不能为空")
    @JsonProperty("app_secret")
    @ApiModelProperty(notes = "应用密钥")
    private String appSecret;
}

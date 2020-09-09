package com.wondernect.services.ums.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondernect.stars.session.dto.code.CodeResponseDTO;
import com.wondernect.stars.user.dto.UserResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@ApiModel(value = "登录响应对象")
public class LoginResponseDTO {

    @JsonProperty("user")
    @ApiModelProperty(notes = "用户信息")
    private UserResponseDTO userResponseDTO;

    @JsonProperty("code")
    @ApiModelProperty(notes = "用户访问令牌")
    private CodeResponseDTO codeResponseDTO;
}

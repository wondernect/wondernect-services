package com.wondernect.services.ums.file.context;

import com.wondernect.elements.authorize.context.impl.AbstractWondernectAuthorizeContext;
import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.response.BusinessData;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.session.dto.code.CodeAuthRequestDTO;
import com.wondernect.stars.session.dto.code.CodeResponseDTO;
import com.wondernect.stars.session.feign.codeSession.CodeSessionFeignClient;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:王威
 * @Date: 2020/7/13 17:12
 * @Version 1.0
 */
@Component
public class AuthorizeUserRoleContext extends AbstractWondernectAuthorizeContext {

    @Autowired
    private CodeSessionFeignClient codeSessionFeignClient;

    @Autowired
    private UserServerService userServerService;

    @Override
    public String authorizeExpiresToken(String authorizeToken) {
        BusinessData<CodeResponseDTO> codeResponseDTOBusinessData = codeSessionFeignClient.authCache(new CodeAuthRequestDTO(authorizeToken));
        if (!codeResponseDTOBusinessData.success()){
            throw  new BusinessException(codeResponseDTOBusinessData);
        }
        return codeResponseDTOBusinessData.getData().getUserId();
    }

    @Override
    public String getUserRole(String userId) {
        UserResponseDTO userResponseDTO = userServerService.detail(userId);
        if (ESObjectUtils.isNull(userResponseDTO)) {
            throw new BusinessException("用户信息不存在");
        }
        return userResponseDTO.getRoleId();
    }

    @Override
    public boolean authorizeAppSecret(String appId, String encryptSecret) {
        return true;
    }
}

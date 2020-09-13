package com.wondernect.services.ums.user.context;

import com.wondernect.elements.authorize.context.impl.AbstractWondernectAuthorizeContext;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.stars.session.dto.code.CodeAuthRequestDTO;
import com.wondernect.stars.session.feign.codeSession.CodeSessionServerService;
import com.wondernect.stars.user.common.error.UserErrorEnum;
import com.wondernect.stars.user.common.exception.UserException;
import com.wondernect.stars.user.model.User;
import com.wondernect.stars.user.service.user.UserService;
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
    private CodeSessionServerService codeSessionServerService;

    @Autowired
    private UserService userService;

    @Override
    public String authorizeExpiresToken(String authorizeToken) {
        return codeSessionServerService.authCache(new CodeAuthRequestDTO(authorizeToken)).getUserId();
    }

    @Override
    public String getUserRole(String userId) {
        User user = userService.findEntityById(userId);
        if (ESObjectUtils.isNull(user)) {
            throw new UserException(UserErrorEnum.USER_NOT_FOUND);
        }
        return user.getRoleId();
    }
}

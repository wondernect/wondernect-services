package com.wondernect.services.ums.open.context;

import com.wondernect.elements.authorize.context.AuthorizeData;
import com.wondernect.elements.authorize.context.impl.AbstractWondernectAuthorizeContext;
import com.wondernect.stars.session.dto.code.CodeAuthRequestDTO;
import com.wondernect.stars.session.dto.code.CodeResponseDTO;
import com.wondernect.stars.session.dto.token.TokenAuthRequestDTO;
import com.wondernect.stars.session.dto.token.TokenResponseDTO;
import com.wondernect.stars.session.feign.codeSession.CodeSessionServerService;
import com.wondernect.stars.session.feign.tokenSession.TokenSessionServerService;
import com.wondernect.stars.user.dto.UserResponseDTO;
import com.wondernect.stars.user.feign.user.UserServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeContext extends AbstractWondernectAuthorizeContext {

    @Autowired
    private CodeSessionServerService codeSessionServerService;

    @Autowired
    private TokenSessionServerService tokenSessionServerService;

    @Autowired
    private UserServerService userServerService;

    @Override
    public AuthorizeData authorizeExpiresToken(String authorizeToken) {
        CodeResponseDTO codeResponseDTO = codeSessionServerService.authCache(new CodeAuthRequestDTO(authorizeToken));
        UserResponseDTO userResponseDTO = userServerService.exist(codeResponseDTO.getUserId());
        return new AuthorizeData(authorizeToken, codeResponseDTO.getUserId(), codeResponseDTO.getCreateApp(), userResponseDTO.getRoleId());
    }

    @Override
    public AuthorizeData authorizeUnlimitedToken(String authorizeToken) {
        TokenResponseDTO tokenResponseDTO = tokenSessionServerService.auth(new TokenAuthRequestDTO(authorizeToken));
        UserResponseDTO userResponseDTO = userServerService.exist(tokenResponseDTO.getUserId());
        return new AuthorizeData(authorizeToken, tokenResponseDTO.getUserId(), tokenResponseDTO.getCreateApp(), userResponseDTO.getRoleId());
    }
}

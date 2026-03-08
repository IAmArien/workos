package com.workos.corporate.presentation.mappers.auth;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticateUserResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticationTokenResponseDto;

public interface AuthenticateUserMapper {
    AuthenticateUserResponseDto toResponseDto(UserAuthentication userAuthentication);
    AuthenticationTokenResponseDto toResponseDto(UserToken userToken);
}

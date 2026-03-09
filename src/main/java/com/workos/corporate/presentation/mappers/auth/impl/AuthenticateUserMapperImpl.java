package com.workos.corporate.presentation.mappers.auth.impl;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticateUserResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticationTokenResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;
import com.workos.corporate.presentation.controllers.user.dto.response.UserDetailsResponseDto;
import com.workos.corporate.presentation.mappers.auth.AuthenticateUserMapper;
import com.workos.corporate.presentation.mappers.auth.UserCredentialsMapper;
import com.workos.corporate.presentation.mappers.user.UserDetailsMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AuthenticateUserMapperImpl implements AuthenticateUserMapper {

    private final UserCredentialsMapper userCredentialsMapper;
    private final UserDetailsMapper userDetailsMapper;

    public AuthenticateUserMapperImpl(UserCredentialsMapper userCredentialsMapper, UserDetailsMapper userDetailsMapper) {
        this.userCredentialsMapper = userCredentialsMapper;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public AuthenticateUserResponseDto toResponseDto(UserAuthentication userAuthentication) {
        UserCredentialsResponseDto userCredentials =
            userCredentialsMapper.toResponseDto(userAuthentication.userCredentials());
        UserDetailsResponseDto userDetails =
            userDetailsMapper.toResponseDto(userAuthentication.userDetails());
        AuthenticationTokenResponseDto token = toResponseDto(userAuthentication.userToken());
        return new AuthenticateUserResponseDto(userCredentials, userDetails, token);
    }

    @Override
    public AuthenticationTokenResponseDto toResponseDto(UserToken userToken) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return new AuthenticationTokenResponseDto(
            userToken.accessToken(),
            userToken.accessTokenExpiresAt().format(formatter),
            userToken.refreshToken(),
            userToken.refreshTokenExpiresAt().format(formatter)
        );
    }
}

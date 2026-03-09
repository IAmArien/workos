package com.workos.corporate.presentation.mappers.auth.impl;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.infrastructure.security.config.PasswordConfig;
import com.workos.corporate.presentation.constants.UserType;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;
import com.workos.corporate.presentation.mappers.auth.UserCredentialsMapper;
import com.workos.corporate.presentation.utils.GlobalUtils;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsMapperImpl implements UserCredentialsMapper {

    private final PasswordConfig passwordConfig;

    public UserCredentialsMapperImpl(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserCredentials toEntity(CreateUserCredentialsRequestDto dto) {
        String userId = GlobalUtils.generateUUID();
        String userPassword = passwordConfig.passwordEncoder().encode(dto.password());
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setUserEmail(dto.emailAddress());
        userCredentials.setUserPassword(userPassword);
        userCredentials.setUserType(UserType.CLIENT);
        return userCredentials;
    }

    @Override
    public UserCredentialsResponseDto toResponseDto(UserCredentials userCredentials) {
        return new UserCredentialsResponseDto(
            userCredentials.getId(),
            userCredentials.getUserId(),
            userCredentials.getUserEmail(),
            userCredentials.getUserType()
        );
    }
}

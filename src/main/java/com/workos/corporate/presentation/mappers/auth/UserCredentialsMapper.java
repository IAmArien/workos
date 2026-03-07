package com.workos.corporate.presentation.mappers.auth;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.presentation.constants.UserType;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserCredentialsMapper {

    public UserCredentials toEntity(CreateUserCredentialsRequestDto dto) {
        String userId = UUID.randomUUID().toString();
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setUserEmail(dto.emailAddress());
        userCredentials.setUserPassword(dto.password());
        userCredentials.setUserType(UserType.CLIENT);
        return userCredentials;
    }

    public UserCredentialsResponseDto toResponseDto(UserCredentials userCredentials) {
        return new UserCredentialsResponseDto(
            userCredentials.getId(),
            userCredentials.getUserId(),
            userCredentials.getUserEmail(),
            userCredentials.getUserType()
        );
    }
}

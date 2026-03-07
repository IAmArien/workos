package com.workos.corporate.presentation.mappers.auth;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsMapper {

    public UserCredentials toEntity(CreateUserCredentialsRequestDto dto) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(dto.getUserId());
        userCredentials.setUserEmail(dto.getUserEmail());
        userCredentials.setUserPassword(dto.getUserPassword());
        userCredentials.setUserType(dto.getUserType());
        return userCredentials;
    }
}

package com.workos.corporate.presentation.mappers.auth;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;

public interface UserCredentialsMapper {
    UserCredentials toEntity(CreateUserCredentialsRequestDto dto);
    UserCredentialsResponseDto toResponseDto(UserCredentials userCredentials);
}

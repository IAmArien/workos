package com.workos.corporate.presentation.controllers.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workos.corporate.presentation.constants.UserType;

public record UserCredentialsResponseDto(
    long id,
    @JsonProperty("user_id") String userId,
    @JsonProperty("email_address")String emailAddress,
    UserType type
) { }

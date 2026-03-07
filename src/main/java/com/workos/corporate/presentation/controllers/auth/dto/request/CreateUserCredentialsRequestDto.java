package com.workos.corporate.presentation.controllers.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserCredentialsRequestDto(
    @JsonProperty("email_address") String emailAddress,
    String password
) { }

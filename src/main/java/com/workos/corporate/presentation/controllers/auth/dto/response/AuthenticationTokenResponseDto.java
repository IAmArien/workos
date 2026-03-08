package com.workos.corporate.presentation.controllers.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationTokenResponseDto(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken
) { }

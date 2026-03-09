package com.workos.corporate.presentation.controllers.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationTokenResponseDto(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("access_token_expires_at") String accessTokenExpiresAt,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("refresh_token_expires_at") String refreshTokenExpiresAt
) { }

package com.workos.corporate.presentation.controllers.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshAuthenticationRequestDto(
    @JsonProperty("refresh_token") String refreshToken
) { }

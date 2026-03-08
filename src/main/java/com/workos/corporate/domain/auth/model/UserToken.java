package com.workos.corporate.domain.auth.model;

public record UserToken(
    String accessToken,
    String refreshToken
) { }

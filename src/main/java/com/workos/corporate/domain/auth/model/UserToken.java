package com.workos.corporate.domain.auth.model;

import java.time.LocalDateTime;

public record UserToken(
    String accessToken,
    LocalDateTime accessTokenExpiresAt,
    String refreshToken,
    LocalDateTime refreshTokenExpiresAt
) { }

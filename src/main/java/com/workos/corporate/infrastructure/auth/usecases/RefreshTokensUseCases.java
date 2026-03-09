package com.workos.corporate.infrastructure.auth.usecases;

import com.workos.corporate.application.auth.CreateRefreshToken;

public interface RefreshTokensUseCases {
    CreateRefreshToken createRefreshToken();
}

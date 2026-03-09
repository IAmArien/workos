package com.workos.corporate.application.auth;

import com.workos.corporate.domain.auth.model.RefreshTokens;

public interface CreateRefreshToken {
    void execute(RefreshTokens refreshTokens);
}

package com.workos.corporate.domain.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;

public interface RefreshTokensDataSource {
    void createRefreshToken(RefreshTokens refreshTokens);
}

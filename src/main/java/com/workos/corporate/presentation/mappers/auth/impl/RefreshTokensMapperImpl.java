package com.workos.corporate.presentation.mappers.auth.impl;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.presentation.mappers.auth.RefreshTokensMapper;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokensMapperImpl implements RefreshTokensMapper {

    @Override
    public RefreshTokens toEntity(UserToken userToken, String sessionId) {
        RefreshTokens refreshTokens = new RefreshTokens();
        refreshTokens.setSessionId(sessionId);
        refreshTokens.setTokenHash(userToken.refreshToken());
        refreshTokens.setExpiresAt(userToken.refreshTokenExpiresAt());
        refreshTokens.setRevoked(false);
        return refreshTokens;
    }
}

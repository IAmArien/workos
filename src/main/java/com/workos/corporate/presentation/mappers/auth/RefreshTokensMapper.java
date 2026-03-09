package com.workos.corporate.presentation.mappers.auth;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.model.UserToken;

public interface RefreshTokensMapper {
    RefreshTokens toEntity(UserToken userToken, String sessionId);
}

package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.repository.RefreshTokensRepository;
import com.workos.corporate.presentation.utils.WebTokenUtils;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokensRepositoryImpl implements RefreshTokensRepository {

    private final RefreshTokensJpaRepository jpa;
    private final WebTokenUtils webTokenUtils;

    public RefreshTokensRepositoryImpl(RefreshTokensJpaRepository jpa, WebTokenUtils webTokenUtils) {
        this.jpa = jpa;
        this.webTokenUtils = webTokenUtils;
    }

    @Override
    public void createRefreshToken(RefreshTokens refreshTokens) {
        String currentRefreshToken = refreshTokens.getTokenHash();
        String hashedRefreshToken = webTokenUtils.hashRefreshToken(currentRefreshToken);
        refreshTokens.setTokenHash(hashedRefreshToken);
        this.jpa.save(refreshTokens);
    }
}

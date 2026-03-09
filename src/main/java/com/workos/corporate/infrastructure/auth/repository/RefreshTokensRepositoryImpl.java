package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.repository.RefreshTokensRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokensRepositoryImpl implements RefreshTokensRepository {

    private final RefreshTokensJpaRepository jpa;

    public RefreshTokensRepositoryImpl(RefreshTokensJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void createRefreshToken(RefreshTokens refreshTokens) {
        this.jpa.save(refreshTokens);
    }
}

package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.repository.RefreshTokensDataSource;
import com.workos.corporate.domain.auth.repository.RefreshTokensRepository;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokensDataSourceImpl implements RefreshTokensDataSource {

    private final RefreshTokensRepository refreshTokensRepository;

    public RefreshTokensDataSourceImpl(RefreshTokensRepository refreshTokensRepository) {
        this.refreshTokensRepository = refreshTokensRepository;
    }

    @Override
    public void createRefreshToken(RefreshTokens refreshTokens) {
        this.refreshTokensRepository.createRefreshToken(refreshTokens);
    }
}

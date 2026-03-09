package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.CreateRefreshToken;
import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.repository.RefreshTokensDataSource;
import org.springframework.stereotype.Service;

@Service
public class CreateRefreshTokenImpl implements CreateRefreshToken {

    private final RefreshTokensDataSource refreshTokensDataSource;

    public CreateRefreshTokenImpl(RefreshTokensDataSource refreshTokensDataSource) {
        this.refreshTokensDataSource = refreshTokensDataSource;
    }

    @Override
    public void execute(RefreshTokens refreshTokens) {
        this.refreshTokensDataSource.createRefreshToken(refreshTokens);
    }
}

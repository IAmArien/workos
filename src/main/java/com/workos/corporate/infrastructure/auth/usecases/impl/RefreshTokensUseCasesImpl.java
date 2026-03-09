package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.CreateRefreshToken;
import com.workos.corporate.infrastructure.auth.usecases.RefreshTokensUseCases;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokensUseCasesImpl implements RefreshTokensUseCases {

    private final CreateRefreshToken createRefreshToken;

    public RefreshTokensUseCasesImpl(CreateRefreshToken createRefreshToken) {
        this.createRefreshToken = createRefreshToken;
    }

    @Override
    public CreateRefreshToken createRefreshToken() {
        return createRefreshToken;
    }
}

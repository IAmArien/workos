package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.RefreshAuthentication;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.domain.auth.repository.AuthDataSource;
import org.springframework.stereotype.Service;

@Service
public class RefreshAuthenticationImpl implements RefreshAuthentication {

    private final AuthDataSource authDataSource;

    public RefreshAuthenticationImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public UserToken execute(String refreshToken) {
        return this.authDataSource.refreshAuthentication(refreshToken);
    }
}

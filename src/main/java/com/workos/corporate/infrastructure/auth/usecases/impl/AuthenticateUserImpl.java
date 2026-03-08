package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.AuthenticateUser;
import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.repository.AuthDataSource;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserImpl implements AuthenticateUser {

    private final AuthDataSource authDataSource;

    public AuthenticateUserImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public UserAuthentication execute(String email, String password) {
        return this.authDataSource.authenticateUser(email, password);
    }
}

package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.CreateUserCredentials;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.repository.AuthDataSource;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCredentialsImpl implements CreateUserCredentials {

    private final AuthDataSource authDataSource;

    public CreateUserCredentialsImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public void execute(UserCredentials userCredentials) {
        this.authDataSource.createUserCredentials(userCredentials);
    }
}

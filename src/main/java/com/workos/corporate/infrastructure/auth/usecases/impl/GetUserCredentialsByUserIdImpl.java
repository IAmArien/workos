package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.GetUserCredentialsByUserId;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.repository.AuthDataSource;
import org.springframework.stereotype.Service;

@Service
public class GetUserCredentialsByUserIdImpl implements GetUserCredentialsByUserId {

    private final AuthDataSource authDataSource;

    public GetUserCredentialsByUserIdImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public UserCredentials execute(String userId) {
        return this.authDataSource.getUserCredentialsByUserId(userId);
    }
}

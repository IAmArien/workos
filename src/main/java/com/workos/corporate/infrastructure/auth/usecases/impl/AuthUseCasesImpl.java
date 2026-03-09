package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.*;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import org.springframework.stereotype.Component;

@Component
public class AuthUseCasesImpl implements AuthUseCases {

    private final GetUserCredentialsByUserId getUserCredentialsByUserId;
    private final GetUserCredentialsByEmail getUserCredentialsByEmail;
    private final CreateUserCredentials createUserCredentials;
    private final AuthenticateUser authenticateUser;
    private final RefreshAuthentication refreshAuthentication;

    public AuthUseCasesImpl(
        GetUserCredentialsByUserId getUserCredentialsByUserId,
        GetUserCredentialsByEmail getUserCredentialsByEmail,
        CreateUserCredentials createUserCredentials,
        AuthenticateUser authenticateUser,
        RefreshAuthentication refreshAuthentication
    ) {
        this.getUserCredentialsByUserId = getUserCredentialsByUserId;
        this.getUserCredentialsByEmail = getUserCredentialsByEmail;
        this.createUserCredentials = createUserCredentials;
        this.authenticateUser = authenticateUser;
        this.refreshAuthentication = refreshAuthentication;
    }

    @Override
    public GetUserCredentialsByUserId getUserCredentialsByUserId() {
        return getUserCredentialsByUserId;
    }

    @Override
    public GetUserCredentialsByEmail getUserCredentialsByEmail() {
        return getUserCredentialsByEmail;
    }

    @Override
    public CreateUserCredentials createUserCredentials() {
        return createUserCredentials;
    }

    @Override
    public AuthenticateUser authenticateUser() {
        return authenticateUser;
    }

    @Override
    public RefreshAuthentication refreshAuthentication() {
        return refreshAuthentication;
    }
}

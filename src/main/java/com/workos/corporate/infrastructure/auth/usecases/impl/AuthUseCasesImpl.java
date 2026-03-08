package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.AuthenticateUser;
import com.workos.corporate.application.auth.CreateUserCredentials;
import com.workos.corporate.application.auth.GetUserCredentialsByEmail;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import org.springframework.stereotype.Component;

@Component
public class AuthUseCasesImpl implements AuthUseCases {

    private final GetUserCredentialsByEmail getUserCredentialsByEmail;
    private final CreateUserCredentials createUserCredentials;
    private final AuthenticateUser authenticateUser;

    public AuthUseCasesImpl(
        GetUserCredentialsByEmail getUserCredentialsByEmail,
        CreateUserCredentials createUserCredentials,
        AuthenticateUser authenticateUser
    ) {
        this.getUserCredentialsByEmail = getUserCredentialsByEmail;
        this.createUserCredentials = createUserCredentials;
        this.authenticateUser = authenticateUser;
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
}

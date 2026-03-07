package com.workos.corporate.infrastructure.auth.usecases.impl;

import com.workos.corporate.application.auth.CreateUserCredentials;
import com.workos.corporate.application.auth.GetUserCredentialsByEmail;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import org.springframework.stereotype.Component;

@Component
public class AuthUseCasesImpl implements AuthUseCases {

    private final GetUserCredentialsByEmail getUserCredentialsByEmail;
    private final CreateUserCredentials createUserCredentials;

    public AuthUseCasesImpl(
        GetUserCredentialsByEmail getUserCredentialsByEmail,
        CreateUserCredentials createUserCredentials
    ) {
        this.getUserCredentialsByEmail = getUserCredentialsByEmail;
        this.createUserCredentials = createUserCredentials;
    }

    @Override
    public GetUserCredentialsByEmail getUserCredentialsByEmail() {
        return getUserCredentialsByEmail;
    }

    @Override
    public CreateUserCredentials createUserCredentials() {
        return createUserCredentials;
    }
}

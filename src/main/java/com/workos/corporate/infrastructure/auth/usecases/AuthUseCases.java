package com.workos.corporate.infrastructure.auth.usecases;

import com.workos.corporate.application.auth.CreateUserCredentials;
import com.workos.corporate.application.auth.GetUserCredentialsByEmail;

public interface AuthUseCases {
    GetUserCredentialsByEmail getUserCredentialsByEmail();
    CreateUserCredentials createUserCredentials();
}

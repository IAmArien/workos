package com.workos.corporate.infrastructure.auth.usecases;

import com.workos.corporate.application.auth.*;

public interface AuthUseCases {
    GetUserCredentialsByUserId getUserCredentialsByUserId();
    GetUserCredentialsByEmail getUserCredentialsByEmail();
    CreateUserCredentials createUserCredentials();
    AuthenticateUser authenticateUser();
    RefreshAuthentication refreshAuthentication();
}

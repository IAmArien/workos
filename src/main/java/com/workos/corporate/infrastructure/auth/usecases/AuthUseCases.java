package com.workos.corporate.infrastructure.auth.usecases;

import com.workos.corporate.application.auth.CreateUserCredentials;
import com.workos.corporate.application.auth.GetUserCredentialsByEmail;
import com.workos.corporate.application.auth.AuthenticateUser;
import com.workos.corporate.application.auth.RefreshAuthentication;

public interface AuthUseCases {
    GetUserCredentialsByEmail getUserCredentialsByEmail();
    CreateUserCredentials createUserCredentials();
    AuthenticateUser authenticateUser();
    RefreshAuthentication refreshAuthentication();
}

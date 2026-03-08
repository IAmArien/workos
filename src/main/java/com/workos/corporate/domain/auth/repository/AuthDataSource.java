package com.workos.corporate.domain.auth.repository;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;

public interface AuthDataSource {
    UserCredentials getUserCredentialsByEmail(String email);
    void createUserCredentials(UserCredentials userCredentials);
    UserAuthentication authenticateUser(String email, String password);
}

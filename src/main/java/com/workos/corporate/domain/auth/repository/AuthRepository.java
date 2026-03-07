package com.workos.corporate.domain.auth.repository;

import com.workos.corporate.domain.auth.model.UserCredentials;

public interface AuthRepository {
    UserCredentials getUserCredentialsByEmail(String email);
    void createUserCredentials(UserCredentials userCredentials);
}

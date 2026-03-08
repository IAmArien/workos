package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.domain.auth.repository.AuthDataSource;
import com.workos.corporate.domain.auth.repository.AuthRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthDataSourceImpl implements AuthDataSource {

    private final AuthRepository authRepository;

    public AuthDataSourceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserCredentials getUserCredentialsByEmail(String email) {
        return this.authRepository.getUserCredentialsByEmail(email);
    }

    @Override
    public void createUserCredentials(UserCredentials userCredentials) {
        this.authRepository.createUserCredentials(userCredentials);
    }

    @Override
    public UserAuthentication authenticateUser(String email, String password) {
        return this.authRepository.authenticateUser(email, password);
    }

    @Override
    public UserToken refreshAuthentication(String refreshToken) {
        return this.authRepository.refreshAuthentication(refreshToken);
    }
}

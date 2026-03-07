package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.repository.AuthRepository;
import com.workos.corporate.shared.exception.NotFoundException;
import com.workos.corporate.shared.exception.UnprocessableEntityException;
import com.workos.corporate.shared.response.ApiErrors;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private final AuthJpaRepository jpa;

    public AuthRepositoryImpl(AuthJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserCredentials getUserCredentialsByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new UnprocessableEntityException("Request parameter email is required.");
        }
        Optional<UserCredentials> userCredentials = this.jpa.findByUserEmail(email);
        return userCredentials.orElseThrow(() ->
            new NotFoundException(String.format("Unable to find user with the email: %s", email))
        );
    }

    @Override
    public void createUserCredentials(UserCredentials userCredentials) {
        if (isValidUserCredentials(userCredentials)) {
            this.jpa.save(userCredentials);
        }
    }

    private boolean isValidUserCredentials(UserCredentials userCredentials) {
        boolean hasError = false;
        UnprocessableEntityException exception =
            new UnprocessableEntityException("Required parameter/s is/are missing.");
        if (userCredentials.getUserEmail().trim().isEmpty()) {
            exception.addError(new ApiErrors("Required parameter email is missing", "EM001"));
            hasError = true;
        }
        if (userCredentials.getUserPassword().trim().isEmpty()) {
            exception.addError(new ApiErrors("Required parameter password is missing", "PW001"));
            hasError = true;
        }
        if (hasError) throw exception;
        return true;
    }
}

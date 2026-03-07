package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.repository.AuthRepository;
import com.workos.corporate.shared.exception.BadRequestException;
import com.workos.corporate.shared.exception.NotFoundException;
import com.workos.corporate.shared.exception.UnprocessableEntityException;
import com.workos.corporate.shared.response.ApiErrors;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private static final String CODE_EMAIL_ALREADY_EXISTS = "EMAE001";
    private static final String CODE_EMAIL_IS_MISSING = "EMIM001";
    private static final String CODE_EMAIL_IS_NON_EXISTENT = "EMINE001";
    private static final String CODE_PASSWORD_IS_MISSING = "PWIM001";

    private final AuthJpaRepository jpa;

    public AuthRepositoryImpl(AuthJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserCredentials getUserCredentialsByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new UnprocessableEntityException("Request parameter email is missing", CODE_EMAIL_IS_MISSING);
        }
        Optional<UserCredentials> userCredentials = this.jpa.findByUserEmail(email);
        return userCredentials.orElseThrow(() ->
            new NotFoundException(
                String.format("Unable to find user with the email: %s", email),
                CODE_EMAIL_IS_NON_EXISTENT
            )
        );
    }

    @Override
    public void createUserCredentials(UserCredentials userCredentials) {
        String userEmail = userCredentials.getUserEmail();
        Optional<UserCredentials> optionalUserCredentials = this.jpa.findByUserEmail(userEmail);
        if (optionalUserCredentials.isPresent()) {
            throw new BadRequestException(
                String.format("Email address for %s already exists", userEmail),
                CODE_EMAIL_ALREADY_EXISTS
            );
        }
        if (isValidUserCredentials(userCredentials)) {
            this.jpa.save(userCredentials);
        }
    }

    private boolean isValidUserCredentials(UserCredentials userCredentials) {
        boolean hasError = false;
        UnprocessableEntityException exception =
            new UnprocessableEntityException("Required parameter/s is/are missing.", null);
        if (userCredentials.getUserEmail().trim().isEmpty()) {
            exception.addError(new ApiErrors("Required parameter email is missing", CODE_EMAIL_IS_MISSING));
            hasError = true;
        }
        if (userCredentials.getUserPassword().trim().isEmpty()) {
            exception.addError(new ApiErrors("Required parameter password is missing", CODE_PASSWORD_IS_MISSING));
            hasError = true;
        }
        if (hasError) throw exception;
        return true;
    }
}

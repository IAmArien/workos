package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.domain.auth.repository.AuthRepository;
import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.infrastructure.security.SecurityConfig;
import com.workos.corporate.infrastructure.user.repository.UserDetailsJpaRepository;
import com.workos.corporate.presentation.utils.WebTokenUtils;
import com.workos.corporate.shared.exception.BadRequestException;
import com.workos.corporate.shared.exception.NotFoundException;
import com.workos.corporate.shared.exception.UnauthorizedException;
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
    private static final String CODE_EM_PASS_IS_MISSING = "EMPWIM001";
    private static final String CODE_EM_PASS_IS_INVALID = "EMPWIINV001";
    private static final String CODE_ACCOUNT_NOT_REGISTERED = "ACCNREG001";

    private final AuthJpaRepository authJpa;
    private final UserDetailsJpaRepository userJpa;
    private final SecurityConfig securityConfig;
    private final WebTokenUtils webTokenUtils;

    public AuthRepositoryImpl(
        AuthJpaRepository authJpa,
        UserDetailsJpaRepository userJpa,
        SecurityConfig securityConfig,
        WebTokenUtils webTokenUtils
    ) {
        this.authJpa = authJpa;
        this.userJpa = userJpa;
        this.securityConfig = securityConfig;
        this.webTokenUtils = webTokenUtils;
    }

    @Override
    public UserCredentials getUserCredentialsByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new UnprocessableEntityException("Request parameter email is missing", CODE_EMAIL_IS_MISSING);
        }
        Optional<UserCredentials> userCredentials = this.authJpa.findByUserEmail(email);
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
        Optional<UserCredentials> optionalUserCredentials = this.authJpa.findByUserEmail(userEmail);
        if (optionalUserCredentials.isPresent()) {
            throw new BadRequestException(
                String.format("Email address for %s already exists", userEmail),
                CODE_EMAIL_ALREADY_EXISTS
            );
        }
        if (isValidUserCredentials(userCredentials)) {
            this.authJpa.save(userCredentials);
        }
    }

    @Override
    public UserAuthentication authenticateUser(String email, String password) {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            throw new BadRequestException("Email address or password must not be empty", CODE_EM_PASS_IS_MISSING);
        }
        Optional<UserCredentials> optionalUserCredentials = this.authJpa.findByUserEmail(email);
        if (optionalUserCredentials.isEmpty()) {
            throw new UnauthorizedException("Email address or password is incorrect", CODE_EM_PASS_IS_INVALID);
        }
        UserCredentials userCredentials = optionalUserCredentials.get();
        boolean isAuthenticated = securityConfig.passwordEncoder().matches(password, userCredentials.getUserPassword());
        if (isAuthenticated) {
            Optional<UserDetails> optionalUserDetails = this.userJpa.findByUserId(userCredentials.getUserId());
            if (optionalUserDetails.isEmpty()) {
                throw new UnauthorizedException(
                    String.format("Account registration for %s is not yet completed", email),
                    CODE_ACCOUNT_NOT_REGISTERED
                );
            }
            UserDetails userDetails = optionalUserDetails.get();
            UserToken userToken = new UserToken(
                webTokenUtils.generateAccessToken(userCredentials.getUserId()),
                webTokenUtils.generateRefreshToken(userCredentials.getUserId())
            );
            return new UserAuthentication(
                userCredentials,
                userDetails,
                userToken
            );
        }
        throw new UnauthorizedException("Email address or password is incorrect", CODE_EM_PASS_IS_INVALID);
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

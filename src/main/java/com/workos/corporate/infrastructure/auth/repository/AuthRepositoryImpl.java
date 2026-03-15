package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.domain.auth.repository.AuthRepository;
import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.infrastructure.security.config.PasswordConfig;
import com.workos.corporate.infrastructure.security.session.SessionIdGenerator;
import com.workos.corporate.infrastructure.user.repository.UserDetailsJpaRepository;
import com.workos.corporate.infrastructure.user.repository.UserSessionsJpaRepository;
import com.workos.corporate.presentation.mappers.auth.RefreshTokensMapper;
import com.workos.corporate.presentation.utils.WebTokenUtils;
import com.workos.corporate.shared.exception.BadRequestException;
import com.workos.corporate.shared.exception.NotFoundException;
import com.workos.corporate.shared.exception.UnauthorizedException;
import com.workos.corporate.shared.exception.UnprocessableEntityException;
import com.workos.corporate.shared.response.ApiErrors;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    private static final String CODE_USER_ID_IS_MISSING = "UDIDIM001";
    private static final String CODE_EMAIL_ALREADY_EXISTS = "EMAE001";
    private static final String CODE_EMAIL_IS_MISSING = "EMIM001";
    private static final String CODE_EMAIL_IS_NON_EXISTENT = "EMINE001";
    private static final String CODE_PASSWORD_IS_MISSING = "PWIM001";
    private static final String CODE_EM_PASS_IS_MISSING = "EMPWIM001";
    private static final String CODE_EM_PASS_IS_INVALID = "EMPWIINV001";
    private static final String CODE_ACCOUNT_NOT_REGISTERED = "ACCNREG001";
    private static final String CODE_REFRESH_TOKEN_IS_MISSING = "RTKIM001";
    private static final String CODE_REFRESH_TOKEN_IS_INVALID = "RTKINV001";

    private final AuthJpaRepository authJpa;
    private final UserDetailsJpaRepository userJpa;
    private final UserSessionsJpaRepository sessionJpa;
    private final RefreshTokensJpaRepository tokenJpa;
    private final PasswordConfig passwordConfig;
    private final WebTokenUtils webTokenUtils;
    private final SessionIdGenerator sessionIdGenerator;
    private final RefreshTokensMapper refreshTokensMapper;

    public AuthRepositoryImpl(
        AuthJpaRepository authJpa,
        UserDetailsJpaRepository userJpa,
        UserSessionsJpaRepository sessionJpa,
        RefreshTokensJpaRepository tokenJpa,
        PasswordConfig passwordConfig,
        WebTokenUtils webTokenUtils,
        SessionIdGenerator sessionIdGenerator,
        RefreshTokensMapper refreshTokensMapper
    ) {
        this.authJpa = authJpa;
        this.userJpa = userJpa;
        this.sessionJpa = sessionJpa;
        this.tokenJpa = tokenJpa;
        this.passwordConfig = passwordConfig;
        this.webTokenUtils = webTokenUtils;
        this.sessionIdGenerator = sessionIdGenerator;
        this.refreshTokensMapper = refreshTokensMapper;
    }

    @Override
    public UserCredentials getUserCredentialsByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new UnprocessableEntityException("Request parameter userId is missing", CODE_USER_ID_IS_MISSING);
        }
        Optional<UserCredentials> userCredentials = this.authJpa.findByUserId(userId);
        return userCredentials.orElseThrow(() ->
            new NotFoundException(
                String.format("Unable to find user with the userId: %s", userId),
                CODE_USER_ID_IS_MISSING
            )
        );
    }

    @Override
    public UserCredentials getUserCredentialsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
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
        if ((email == null || password == null) || (email.trim().isEmpty() || password.trim().isEmpty())) {
            throw new BadRequestException("Email address or password must not be empty", CODE_EM_PASS_IS_MISSING);
        }
        UserCredentials userCredentials = this.authJpa.findByUserEmail(email).orElseThrow(() ->
            new UnauthorizedException("Email address or password is incorrect", CODE_EM_PASS_IS_INVALID));
        if (passwordConfig.passwordEncoder().matches(password, userCredentials.getUserPassword())) {
            UserDetails userDetails = this.userJpa.findByUserId(userCredentials.getUserId()).orElseThrow(() ->
                new UnauthorizedException(
                    String.format("Account registration for %s is not yet completed", email),
                    CODE_ACCOUNT_NOT_REGISTERED
                ));
            String sessionToken = sessionIdGenerator.generate();
            Pair<String, LocalDateTime> accessTokens =
                webTokenUtils.generateAccessToken(userCredentials.getUserId(), sessionToken);
            Pair<String, LocalDateTime> refreshTokens =
                webTokenUtils.generateRefreshToken(userCredentials.getUserId(), sessionToken);
            UserToken userToken = new UserToken(
                accessTokens.a,
                accessTokens.b,
                refreshTokens.a,
                refreshTokens.b
            );
            return UserAuthentication.create(userCredentials, userDetails, userToken, sessionToken);
        }
        throw new UnauthorizedException("Email address or password is incorrect", CODE_EM_PASS_IS_INVALID);
    }

    @Override
    public UserToken refreshAuthentication(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new BadRequestException("Refresh token must not be empty", CODE_REFRESH_TOKEN_IS_MISSING);
        }
        if (webTokenUtils.validateToken(refreshToken)) {
            String userId = webTokenUtils.extractUserId(refreshToken);
            String sessionToken = webTokenUtils.extractSessionToken(refreshToken);

            UserSessions userSessions = this.sessionJpa.findBySessionId(sessionToken).orElseThrow(() ->
                new IllegalStateException("Invalid session token provided"));
            if (userSessions.getRevoked()) {
                throw new UnauthorizedException("Refresh token is invalid", CODE_REFRESH_TOKEN_IS_INVALID);
            }

            String hashedToken = webTokenUtils.hashRefreshToken(refreshToken);
            RefreshTokens refreshTokens = this.tokenJpa.findBySessionIdAndTokenHash(sessionToken, hashedToken).orElseThrow(() ->
                new UnauthorizedException("Refresh token is invalid", CODE_REFRESH_TOKEN_IS_INVALID));
            if (refreshTokens.getRevoked()) {
                invalidateAllSessions(userId);
                throw new UnauthorizedException("Refresh token is invalid", CODE_REFRESH_TOKEN_IS_INVALID);
            }

            UserCredentials userCredentials = this.authJpa.findByUserId(userId).orElseThrow(() ->
                new UnauthorizedException("Refresh token is invalid", CODE_REFRESH_TOKEN_IS_INVALID));

            invalidateTokenSession(refreshTokens);

            Pair<String, LocalDateTime> newAccessToken =
                webTokenUtils.generateAccessToken(userCredentials.getUserId(), sessionToken);
            Pair<String, LocalDateTime> newRefreshToken =
                webTokenUtils.generateRefreshToken(userCredentials.getUserId(), sessionToken);

            UserToken userToken = new UserToken(
                newAccessToken.a,
                newAccessToken.b,
                newRefreshToken.a,
                newRefreshToken.b
            );

            RefreshTokens newRefreshTokens = refreshTokensMapper.toEntity(userToken, sessionToken);
            newRefreshTokens.setTokenHash(webTokenUtils.hashRefreshToken(userToken.refreshToken()));
            this.tokenJpa.save(newRefreshTokens);

            return userToken;
        }
        throw new UnauthorizedException("Refresh token is invalid", CODE_REFRESH_TOKEN_IS_INVALID);
    }

    private void invalidateTokenSession(RefreshTokens refreshTokens) {
        refreshTokens.setRevoked(true);
        this.tokenJpa.save(refreshTokens);
    }

    @Transactional
    private void invalidateAllSessions(String userId) {
        List<UserSessions> userSessionsList = this.sessionJpa.findAllByUserId(userId);
        userSessionsList.forEach(session -> session.setRevoked(true));
        this.sessionJpa.saveAll(userSessionsList);
        this.tokenJpa.revokeAllByUserId(userId);
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

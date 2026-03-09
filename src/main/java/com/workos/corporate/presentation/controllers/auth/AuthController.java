package com.workos.corporate.presentation.controllers.auth;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.auth.model.UserToken;
import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import com.workos.corporate.infrastructure.auth.usecases.RefreshTokensUseCases;
import com.workos.corporate.infrastructure.user.usecases.UserSessionsUseCases;
import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.presentation.controllers.auth.dto.request.AuthenticateUserRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.request.RefreshAuthenticationRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticateUserResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticationTokenResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;
import com.workos.corporate.presentation.mappers.auth.AuthenticateUserMapper;
import com.workos.corporate.presentation.mappers.auth.RefreshTokensMapper;
import com.workos.corporate.presentation.mappers.auth.UserCredentialsMapper;
import com.workos.corporate.presentation.mappers.user.UserSessionsMapper;
import com.workos.corporate.shared.response.ApiResponse;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUseCases useCases;
    private final UserSessionsUseCases userSessionsUseCases;
    private final RefreshTokensUseCases refreshTokensUseCases;

    private final UserCredentialsMapper userCredentialsMapper;
    private final AuthenticateUserMapper authenticateUserMapper;
    private final UserSessionsMapper userSessionsMapper;
    private final RefreshTokensMapper refreshTokensMapper;

    public AuthController(
        AuthUseCases useCases,
        UserSessionsUseCases userSessionsUseCases,
        RefreshTokensUseCases refreshTokensUseCases,
        UserCredentialsMapper userCredentialsMapper,
        AuthenticateUserMapper authenticateUserMapper,
        UserSessionsMapper userSessionsMapper,
        RefreshTokensMapper refreshTokensMapper
    ) {
        this.useCases = useCases;
        this.userSessionsUseCases = userSessionsUseCases;
        this.refreshTokensUseCases = refreshTokensUseCases;
        this.userCredentialsMapper = userCredentialsMapper;
        this.authenticateUserMapper = authenticateUserMapper;
        this.userSessionsMapper = userSessionsMapper;
        this.refreshTokensMapper = refreshTokensMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserCredentialsResponseDto>> getUserCredentialsByEmail(@RequestParam String email) {
        UserCredentials userCredentials = useCases.getUserCredentialsByEmail().execute(email);
        UserCredentialsResponseDto responseDto = userCredentialsMapper.toResponseDto(userCredentials);
        return ApiResponse.success(responseDto, HttpResponseStatus.SUCCESS, "User fetched successfully")
            .asEntity();
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<UserCredentialsResponseDto>> createUserCredentials(
        @RequestBody CreateUserCredentialsRequestDto dto
    ) {
        useCases.createUserCredentials().execute(userCredentialsMapper.toEntity(dto));
        UserCredentials userCredentials = useCases.getUserCredentialsByEmail().execute(dto.emailAddress());
        UserCredentialsResponseDto responseDto = userCredentialsMapper.toResponseDto(userCredentials);
        return ApiResponse.success(responseDto, HttpResponseStatus.CREATED, "User created successfully")
            .asEntity();
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthenticateUserResponseDto>> authenticateUser(
        @RequestBody AuthenticateUserRequestDto dto,
        @Nullable @RequestHeader("User-Agent") String userAgent
    ) {
        UserAuthentication userAuthentication = useCases.authenticateUser().execute(dto.email(), dto.password());
        AuthenticateUserResponseDto responseDto = authenticateUserMapper.toResponseDto(userAuthentication);

        String userId = userAuthentication.userCredentials().getUserId();
        UserSessions userSessions = userSessionsMapper.toEntity(dto.userDevice(), userId, userAgent);
        userSessionsUseCases.createUserSession().execute(userSessions);

        String sessionId = userSessions.getSessionId();
        RefreshTokens refreshTokens = refreshTokensMapper.toEntity(userAuthentication.userToken(), sessionId);
        refreshTokensUseCases.createRefreshToken().execute(refreshTokens);

        return ApiResponse.success(responseDto, HttpResponseStatus.SUCCESS, "User logged in successfully")
            .asEntity();
    }

    @PostMapping("refresh")
    public ResponseEntity<ApiResponse<AuthenticationTokenResponseDto>> refreshAuthentication(
        @RequestBody RefreshAuthenticationRequestDto dto
    ) {
        UserToken userToken = useCases.refreshAuthentication().execute(dto.refreshToken());
        AuthenticationTokenResponseDto responseDto = authenticateUserMapper.toResponseDto(userToken);
        return ApiResponse.success(responseDto, HttpResponseStatus.CREATED, "Refresh token successful")
            .asEntity();
    }
}

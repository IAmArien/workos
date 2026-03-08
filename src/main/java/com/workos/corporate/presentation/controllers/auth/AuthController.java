package com.workos.corporate.presentation.controllers.auth;

import com.workos.corporate.domain.auth.model.UserAuthentication;
import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.presentation.controllers.auth.dto.request.AuthenticateUserRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.request.CreateUserCredentialsRequestDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.AuthenticateUserResponseDto;
import com.workos.corporate.presentation.controllers.auth.dto.response.UserCredentialsResponseDto;
import com.workos.corporate.presentation.mappers.auth.AuthenticateUserMapper;
import com.workos.corporate.presentation.mappers.auth.UserCredentialsMapper;
import com.workos.corporate.shared.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUseCases useCases;
    private final UserCredentialsMapper userCredentialsMapper;
    private final AuthenticateUserMapper authenticateUserMapper;

    public AuthController(
        AuthUseCases useCases,
        UserCredentialsMapper userCredentialsMapper,
        AuthenticateUserMapper authenticateUserMapper
    ) {
        this.useCases = useCases;
        this.userCredentialsMapper = userCredentialsMapper;
        this.authenticateUserMapper = authenticateUserMapper;
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
        @RequestBody AuthenticateUserRequestDto dto
    ) {
        UserAuthentication userAuthentication = useCases.authenticateUser().execute(dto.email(), dto.password());
        AuthenticateUserResponseDto responseDto = authenticateUserMapper.toResponseDto(userAuthentication);
        return ApiResponse.success(responseDto, HttpResponseStatus.SUCCESS, "User logged in successfully")
            .asEntity();
    }
}

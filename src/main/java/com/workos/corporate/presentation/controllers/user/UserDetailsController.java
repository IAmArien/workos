package com.workos.corporate.presentation.controllers.user;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import com.workos.corporate.infrastructure.user.usecases.UserDetailsUseCases;
import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.presentation.controllers.user.dto.request.CreateUserDetailsRequestDto;
import com.workos.corporate.presentation.controllers.user.dto.response.UserDetailsResponseDto;
import com.workos.corporate.presentation.mappers.user.UserDetailsMapper;
import com.workos.corporate.shared.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class UserDetailsController {

    private final AuthUseCases authUseCases;
    private final UserDetailsUseCases userDetailsUseCases;
    private final UserDetailsMapper userDetailsMapper;

    public UserDetailsController(
        AuthUseCases authUseCases,
        UserDetailsUseCases userDetailsUseCases,
        UserDetailsMapper userDetailsMapper
    ) {
        this.authUseCases = authUseCases;
        this.userDetailsUseCases = userDetailsUseCases;
        this.userDetailsMapper = userDetailsMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetailsResponseDto>> getUserDetailsById(@PathVariable String userId) {
        UserDetails userDetails = userDetailsUseCases.getUserDetailsById().execute(userId);
        UserDetailsResponseDto responseDto = userDetailsMapper.toResponseDto(userDetails);
        return ApiResponse.success(responseDto, HttpResponseStatus.SUCCESS, "User details fetched successfully")
            .asEntity();
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<UserDetailsResponseDto>> createUserDetails(
        @RequestBody CreateUserDetailsRequestDto dto
    ) {
        UserCredentials userCredentials = authUseCases.getUserCredentialsByEmail().execute(dto.emailAddress());
        userDetailsUseCases.createUserDetails().execute(userDetailsMapper.toEntity(dto, userCredentials.getUserId()));
        UserDetails userDetails = userDetailsUseCases.getUserDetailsById().execute(userCredentials.getUserId());
        UserDetailsResponseDto responseDto = userDetailsMapper.toResponseDto(userDetails);
        return ApiResponse.success(responseDto, HttpResponseStatus.CREATED, "User details created successfully")
            .asEntity();
    }
}

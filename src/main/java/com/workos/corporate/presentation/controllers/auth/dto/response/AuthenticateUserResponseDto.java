package com.workos.corporate.presentation.controllers.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workos.corporate.presentation.controllers.user.dto.response.UserDetailsResponseDto;

public record AuthenticateUserResponseDto(
    @JsonProperty("user_credentials")
    UserCredentialsResponseDto userCredentialsResponseDto,
    @JsonProperty("user_details")
    UserDetailsResponseDto userDetailsResponseDto,
    @JsonProperty("user_token")
    AuthenticationTokenResponseDto authenticationTokenResponseDto
) { }

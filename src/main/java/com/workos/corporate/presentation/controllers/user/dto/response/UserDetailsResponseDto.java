package com.workos.corporate.presentation.controllers.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record UserDetailsResponseDto (
    long id,
    @JsonProperty("user_id") String userId,
    @JsonProperty("first_name") String firstName,
    @Nullable @JsonProperty("middle_name") String middleName,
    @JsonProperty("last_name") String lastName,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("phone_country_code") String phoneCountryCode
) { }

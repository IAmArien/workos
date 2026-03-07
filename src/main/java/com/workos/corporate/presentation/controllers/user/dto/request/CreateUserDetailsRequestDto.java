package com.workos.corporate.presentation.controllers.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record CreateUserDetailsRequestDto(
    @JsonProperty("email_address") String emailAddress,
    @JsonProperty("first_name") String firstName,
    @Nullable @JsonProperty("middle_name") String middleName,
    @JsonProperty("last_name") String lastName,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("phone_country_code") String phoneCountryCode
) { }

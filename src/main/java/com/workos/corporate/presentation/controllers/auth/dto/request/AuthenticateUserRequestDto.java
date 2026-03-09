package com.workos.corporate.presentation.controllers.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticateUserRequestDto(
    @JsonProperty("email_address") String email,
    @JsonProperty("password") String password,
    @JsonProperty("device_details") UserDeviceRequestDto userDevice
) { }

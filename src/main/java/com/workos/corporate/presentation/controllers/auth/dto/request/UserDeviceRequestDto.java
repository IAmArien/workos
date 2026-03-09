package com.workos.corporate.presentation.controllers.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record UserDeviceRequestDto(
    @JsonProperty("device_id") String deviceId,
    @JsonProperty("device_name") String deviceName,
    @Nullable @JsonProperty("ip_address") String ipAddress
) { }

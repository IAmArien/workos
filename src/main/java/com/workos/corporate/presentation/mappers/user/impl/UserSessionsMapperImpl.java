package com.workos.corporate.presentation.mappers.user.impl;

import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.presentation.controllers.auth.dto.request.UserDeviceRequestDto;
import com.workos.corporate.presentation.mappers.user.UserSessionsMapper;
import org.springframework.stereotype.Component;

@Component
public class UserSessionsMapperImpl implements UserSessionsMapper {

    @Override
    public UserSessions toEntity(UserDeviceRequestDto dto, String userId, String sessionToken, String userAgent) {
        UserSessions userSessions = new UserSessions();
        userSessions.setSessionId(sessionToken);
        userSessions.setUserId(userId);
        userSessions.setDeviceId(dto.deviceId());
        userSessions.setDeviceName(dto.deviceName());
        userSessions.setIpAddress(dto.ipAddress());
        userSessions.setUserAgent(userAgent);
        userSessions.setRevoked(false);
        return userSessions;
    }
}

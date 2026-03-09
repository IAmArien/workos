package com.workos.corporate.presentation.mappers.user.impl;

import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.infrastructure.security.session.SessionIdGenerator;
import com.workos.corporate.presentation.controllers.auth.dto.request.UserDeviceRequestDto;
import com.workos.corporate.presentation.mappers.user.UserSessionsMapper;
import org.springframework.stereotype.Component;

@Component
public class UserSessionsMapperImpl implements UserSessionsMapper {

    private final SessionIdGenerator sessionIdGenerator;

    public UserSessionsMapperImpl(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    @Override
    public UserSessions toEntity(UserDeviceRequestDto dto, String userId, String userAgent) {
        UserSessions userSessions = new UserSessions();
        userSessions.setSessionId(sessionIdGenerator.generate());
        userSessions.setUserId(userId);
        userSessions.setDeviceId(dto.deviceId());
        userSessions.setDeviceName(dto.deviceName());
        userSessions.setIpAddress(dto.ipAddress());
        userSessions.setUserAgent(userAgent);
        userSessions.setRevoked(false);
        return userSessions;
    }
}

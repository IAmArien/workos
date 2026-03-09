package com.workos.corporate.presentation.mappers.user;

import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.presentation.controllers.auth.dto.request.UserDeviceRequestDto;

public interface UserSessionsMapper {
    UserSessions toEntity(UserDeviceRequestDto dto, String userId, String userAgent);
}

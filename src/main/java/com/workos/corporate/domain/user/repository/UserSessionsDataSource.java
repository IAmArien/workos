package com.workos.corporate.domain.user.repository;

import com.workos.corporate.domain.user.model.UserSessions;

public interface UserSessionsDataSource {
    void createUserSession(UserSessions sessions);
    UserSessions getUserSessionBySessionId(String sessionId);
}

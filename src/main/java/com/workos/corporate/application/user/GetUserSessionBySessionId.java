package com.workos.corporate.application.user;

import com.workos.corporate.domain.user.model.UserSessions;

public interface GetUserSessionBySessionId {
    UserSessions execute(String sessionId);
}

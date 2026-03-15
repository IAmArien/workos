package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.GetUserSessionBySessionId;
import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.domain.user.repository.UserSessionsDataSource;
import org.springframework.stereotype.Service;

@Service
public class GetUserSessionBySessionIdImpl implements GetUserSessionBySessionId {

    private final UserSessionsDataSource userSessionsDataSource;

    public GetUserSessionBySessionIdImpl(UserSessionsDataSource userSessionsDataSource) {
        this.userSessionsDataSource = userSessionsDataSource;
    }

    @Override
    public UserSessions execute(String sessionId) {
        return this.userSessionsDataSource.getUserSessionBySessionId(sessionId);
    }
}

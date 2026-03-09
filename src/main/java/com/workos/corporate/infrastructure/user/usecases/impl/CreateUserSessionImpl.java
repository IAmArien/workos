package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.CreateUserSession;
import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.domain.user.repository.UserSessionsDataSource;
import org.springframework.stereotype.Service;

@Service
public class CreateUserSessionImpl implements CreateUserSession {

    private final UserSessionsDataSource dataSource;

    public CreateUserSessionImpl(UserSessionsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void execute(UserSessions sessions) {
        this.dataSource.createUserSession(sessions);
    }
}

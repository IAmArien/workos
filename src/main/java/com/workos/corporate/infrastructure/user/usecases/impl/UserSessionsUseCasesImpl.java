package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.CreateUserSession;
import com.workos.corporate.application.user.GetUserSessionBySessionId;
import com.workos.corporate.infrastructure.user.usecases.UserSessionsUseCases;
import org.springframework.stereotype.Component;

@Component
public class UserSessionsUseCasesImpl implements UserSessionsUseCases {

    private final CreateUserSession createUserSession;
    private final GetUserSessionBySessionId getUserSessionBySessionId;

    public UserSessionsUseCasesImpl(
        CreateUserSession createUserSession,
        GetUserSessionBySessionId getUserSessionBySessionId
    ) {
        this.createUserSession = createUserSession;
        this.getUserSessionBySessionId = getUserSessionBySessionId;
    }

    @Override
    public CreateUserSession createUserSession() {
        return createUserSession;
    }

    @Override
    public GetUserSessionBySessionId getUserSessionBySessionId() {
        return getUserSessionBySessionId;
    }
}

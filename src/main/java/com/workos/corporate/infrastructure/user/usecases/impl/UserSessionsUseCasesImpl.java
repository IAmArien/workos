package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.CreateUserSession;
import com.workos.corporate.infrastructure.user.usecases.UserSessionsUseCases;
import org.springframework.stereotype.Component;

@Component
public class UserSessionsUseCasesImpl implements UserSessionsUseCases {

    private final CreateUserSession createUserSession;

    public UserSessionsUseCasesImpl(CreateUserSession createUserSession) {
        this.createUserSession = createUserSession;
    }

    @Override
    public CreateUserSession createUserSession() {
        return createUserSession;
    }
}

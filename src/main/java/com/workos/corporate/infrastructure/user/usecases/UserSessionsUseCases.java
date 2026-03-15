package com.workos.corporate.infrastructure.user.usecases;

import com.workos.corporate.application.user.CreateUserSession;
import com.workos.corporate.application.user.GetUserSessionBySessionId;

public interface UserSessionsUseCases {
    CreateUserSession createUserSession();
    GetUserSessionBySessionId getUserSessionBySessionId();
}

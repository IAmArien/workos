package com.workos.corporate.application.auth;

import com.workos.corporate.domain.auth.model.UserCredentials;

public interface CreateUserCredentials {
    void execute(UserCredentials userCredentials);
}

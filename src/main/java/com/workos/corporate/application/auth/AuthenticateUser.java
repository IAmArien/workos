package com.workos.corporate.application.auth;

import com.workos.corporate.domain.auth.model.UserAuthentication;

public interface AuthenticateUser {
    UserAuthentication execute(String email, String password);
}

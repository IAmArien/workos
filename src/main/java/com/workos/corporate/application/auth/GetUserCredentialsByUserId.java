package com.workos.corporate.application.auth;

import com.workos.corporate.domain.auth.model.UserCredentials;

public interface GetUserCredentialsByUserId {
    UserCredentials execute(String userId);
}

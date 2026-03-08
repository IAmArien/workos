package com.workos.corporate.application.auth;

import com.workos.corporate.domain.auth.model.UserToken;

public interface RefreshAuthentication {
    UserToken execute(String refreshToken);
}

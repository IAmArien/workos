package com.workos.corporate.domain.auth.model;

import com.workos.corporate.domain.user.model.UserDetails;

public record UserAuthentication(
    UserCredentials userCredentials,
    UserDetails userDetails,
    UserToken userToken
) {
    public static UserAuthentication create(
        UserCredentials userCredentials,
        UserDetails userDetails,
        UserToken userToken
    ) {
        return new UserAuthentication(userCredentials, userDetails, userToken);
    }
}


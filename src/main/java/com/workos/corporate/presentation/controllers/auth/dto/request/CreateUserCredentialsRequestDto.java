package com.workos.corporate.presentation.controllers.auth.dto.request;

import com.workos.corporate.presentation.constants.UserType;

public class CreateUserCredentialsRequestDto {
    private String userId;
    private String userEmail;
    private String userPassword;
    private UserType userType;

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserType getUserType() {
        return userType;
    }
}

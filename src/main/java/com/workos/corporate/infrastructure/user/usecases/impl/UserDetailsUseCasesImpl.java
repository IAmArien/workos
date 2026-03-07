package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.CreateUserDetails;
import com.workos.corporate.application.user.GetUserDetailsById;
import com.workos.corporate.infrastructure.user.usecases.UserDetailsUseCases;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsUseCasesImpl implements UserDetailsUseCases {

    private final GetUserDetailsById getUserDetailsById;
    private final CreateUserDetails createUserDetails;

    public UserDetailsUseCasesImpl(GetUserDetailsById getUserDetailsById, CreateUserDetails createUserDetails) {
        this.getUserDetailsById = getUserDetailsById;
        this.createUserDetails = createUserDetails;
    }

    @Override
    public GetUserDetailsById getUserDetailsById() {
        return this.getUserDetailsById;
    }

    @Override
    public CreateUserDetails createUserDetails() {
        return this.createUserDetails;
    }
}

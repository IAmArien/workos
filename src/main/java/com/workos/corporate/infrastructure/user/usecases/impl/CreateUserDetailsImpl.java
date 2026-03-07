package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.CreateUserDetails;
import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.domain.user.repository.UserDetailsDataSource;
import org.springframework.stereotype.Service;

@Service
public class CreateUserDetailsImpl implements CreateUserDetails {

    private final UserDetailsDataSource userDetailsDataSource;

    public CreateUserDetailsImpl(UserDetailsDataSource userDetailsDataSource) {
        this.userDetailsDataSource = userDetailsDataSource;
    }

    @Override
    public void execute(UserDetails userDetails) {
        this.userDetailsDataSource.createUserDetails(userDetails);
    }
}

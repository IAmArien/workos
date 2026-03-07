package com.workos.corporate.infrastructure.user.usecases.impl;

import com.workos.corporate.application.user.GetUserDetailsById;
import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.domain.user.repository.UserDetailsDataSource;
import org.springframework.stereotype.Service;

@Service
public class GetUserDetailsByIdImpl implements GetUserDetailsById {

    private final UserDetailsDataSource userDetailsDataSource;

    public GetUserDetailsByIdImpl(UserDetailsDataSource userDetailsDataSource) {
        this.userDetailsDataSource = userDetailsDataSource;
    }

    @Override
    public UserDetails execute(String userId) {
        return this.userDetailsDataSource.getUserDetailsById(userId);
    }
}

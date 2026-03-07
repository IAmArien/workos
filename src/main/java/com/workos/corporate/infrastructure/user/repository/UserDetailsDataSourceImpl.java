package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserDetails;
import com.workos.corporate.domain.user.repository.UserDetailsDataSource;
import com.workos.corporate.domain.user.repository.UserDetailsRepository;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsDataSourceImpl implements UserDetailsDataSource {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsDataSourceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails getUserDetailsById(String userId) {
        return this.userDetailsRepository.getUserDetailsById(userId);
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        this.userDetailsRepository.createUserDetails(userDetails);
    }
}

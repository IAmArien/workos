package com.workos.corporate.domain.user.repository;

import com.workos.corporate.domain.user.model.UserDetails;

public interface UserDetailsRepository {
    UserDetails getUserDetailsById(String userId);
    void createUserDetails(UserDetails userDetails);
}

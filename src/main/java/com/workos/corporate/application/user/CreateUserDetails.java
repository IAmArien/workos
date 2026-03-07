package com.workos.corporate.application.user;

import com.workos.corporate.domain.user.model.UserDetails;

public interface CreateUserDetails {
    void execute(UserDetails userDetails);
}

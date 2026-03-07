package com.workos.corporate.application.user;

import com.workos.corporate.domain.user.model.UserDetails;

public interface GetUserDetailsById {
    UserDetails execute(String userId);
}

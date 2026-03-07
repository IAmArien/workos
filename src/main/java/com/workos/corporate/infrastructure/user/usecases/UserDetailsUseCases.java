package com.workos.corporate.infrastructure.user.usecases;

import com.workos.corporate.application.user.CreateUserDetails;
import com.workos.corporate.application.user.GetUserDetailsById;

public interface UserDetailsUseCases {
    GetUserDetailsById getUserDetailsById();
    CreateUserDetails createUserDetails();
}

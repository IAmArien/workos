package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.domain.user.repository.UserSessionRepository;
import com.workos.corporate.domain.user.repository.UserSessionsDataSource;
import org.springframework.stereotype.Component;

@Component
public class UserSessionsDataSourceImpl implements UserSessionsDataSource {

    private final UserSessionRepository userSessionRepository;

    public UserSessionsDataSourceImpl(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public void createUserSession(UserSessions sessions) {
        this.userSessionRepository.createUserSession(sessions);
    }
}

package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserSessions;
import com.workos.corporate.domain.user.repository.UserSessionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserSessionsRepositoryImpl implements UserSessionRepository {

    private final UserSessionsJpaRepository jpa;

    public UserSessionsRepositoryImpl(UserSessionsJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void createUserSession(UserSessions sessions) {
        this.jpa.save(sessions);
    }

    @Override
    public UserSessions getUserSessionBySessionId(String sessionId) {
        return this.jpa.findBySessionId(sessionId)
            .orElseThrow(() ->
                new IllegalStateException("Invalid session_id parameter provided")
            );
    }
}

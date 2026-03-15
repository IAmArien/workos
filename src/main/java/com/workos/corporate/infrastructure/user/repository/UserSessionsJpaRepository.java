package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionsJpaRepository extends JpaRepository<UserSessions, Long> {
    Optional<UserSessions> findBySessionId(String sessionId);
    List<UserSessions> findAllByUserId(String userId);
}

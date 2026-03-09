package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionsJpaRepository extends JpaRepository<UserSessions, Long> { }

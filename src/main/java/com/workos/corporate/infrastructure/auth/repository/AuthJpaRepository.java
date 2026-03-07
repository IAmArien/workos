package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthJpaRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUserEmail(String userEmail);
}

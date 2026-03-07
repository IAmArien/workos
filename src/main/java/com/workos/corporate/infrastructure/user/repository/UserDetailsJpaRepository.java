package com.workos.corporate.infrastructure.user.repository;

import com.workos.corporate.domain.user.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsJpaRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUserId(String userId);
    Optional<UserDetails> findByEmailAddress(String emailAddress);
}

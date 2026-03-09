package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokensJpaRepository extends JpaRepository<RefreshTokens, Long> { }

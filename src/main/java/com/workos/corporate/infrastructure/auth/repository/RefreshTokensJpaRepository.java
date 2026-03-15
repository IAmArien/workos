package com.workos.corporate.infrastructure.auth.repository;

import com.workos.corporate.domain.auth.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokensJpaRepository extends JpaRepository<RefreshTokens, Long> {
    Optional<RefreshTokens> findBySessionIdAndTokenHash(String sessionId, String tokenHash);
    @Modifying
    @Transactional
    @Query("""
        UPDATE RefreshTokens t
        SET t.revoked = true,
            t.updatedAt = CURRENT_TIMESTAMP
        WHERE t.sessionId IN (
            SELECT s.sessionId
            FROM UserSessions s
            WHERE s.userId = :userId
        )
    """)
    void revokeAllByUserId(String userId);
}

package com.workos.corporate.presentation.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class WebTokenUtils {

    @Value("${jwt.secret}") private String jwtSecret;
    @Value("${jwt.access-token-expiration}") private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}") private long refreshTokenExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Pair<String, LocalDateTime> generateAccessToken(String userId) {
        Date expiration = new Date(System.currentTimeMillis() + accessTokenExpiration);
        LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String accessToken = Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
        return new Pair<>(accessToken, localDateTime);
    }

    public Pair<String, LocalDateTime> generateRefreshToken(String userId) {
        Date expiration = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String refreshToken = Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
        return new Pair<>(refreshToken, localDateTime);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public String getCurrentAuthenticatedUser() {
        return getCurrentAuthenticatedUserId().orElseThrow(() ->
            new IllegalStateException("No current authenticated user found")
        );
    }

    private Optional<String> getCurrentAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return Optional.empty();
        }
        return Optional.of(authentication.getPrincipal().toString());
    }
}

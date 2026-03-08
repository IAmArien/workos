package com.workos.corporate.presentation.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class WebTokenUtils {

    @Value("${jwt.secret}") private String jwtSecret;
    @Value("${jwt.access-token-expiration}") private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}") private long refreshTokenExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
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

    public boolean isValidPublicEndpoint(@NonNull HttpServletRequest request) {
        List<String> validPublicEndpoints = List.of(
            "/auth/login",
            "/auth/refresh",
            "/auth/create",
            "/users/create"
        );
        boolean isValidPublicEndpoint = false;
        for (String validPublicEndpoint : validPublicEndpoints) {
            if (request.getServletPath().endsWith(validPublicEndpoint)) {
                isValidPublicEndpoint = true;
            }
        }
        return isValidPublicEndpoint;
    }

    public String getAuthenticatedUser() {
        return (String) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}

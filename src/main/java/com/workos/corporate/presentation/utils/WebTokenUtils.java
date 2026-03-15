package com.workos.corporate.presentation.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Component
public class WebTokenUtils {

    @Value("${jwt.secret}") private String jwtSecret;
    @Value("${jwt.access-token-expiration}") private long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}") private long refreshTokenExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Pair<String, LocalDateTime> generateAccessToken(String userId, String token) {
        Date expiration = new Date(System.currentTimeMillis() + accessTokenExpiration);
        LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        HashMap<String, Object> sessionToken = new HashMap<>();
        sessionToken.put("session_token", token);
        String accessToken = Jwts.builder()
            .setSubject(userId)
            .addClaims(sessionToken)
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
        return new Pair<>(accessToken, localDateTime);
    }

    public Pair<String, LocalDateTime> generateRefreshToken(String userId, String token) {
        Date expiration = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        LocalDateTime localDateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        HashMap<String, Object> sessionToken = new HashMap<>();
        sessionToken.put("session_token", token);
        String refreshToken = Jwts.builder()
            .setSubject(userId)
            .addClaims(sessionToken)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
        return new Pair<>(refreshToken, localDateTime);
    }

    public String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing refresh token", e);
        }
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

    public String extractSessionToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.get("session_token", String.class);
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

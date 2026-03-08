package com.workos.corporate.infrastructure.security.impl;

import com.workos.corporate.infrastructure.security.SecurityEndpoints;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class SecurityEndpointsImpl implements SecurityEndpoints {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private SecurityEndpointsImpl() {}

    private final String[] PUBLIC_ENDPOINTS = {
        "/api/v1/auth/login",
        "/api/v1/auth/refresh",
        "/api/v1/auth/create",
        "/api/v1/users/create",
        "/api/v1/health",
        "/api/v1/public/**"
    };

    @Override
    public String[] publicEndpoints() {
        return PUBLIC_ENDPOINTS;
    }

    @Override
    public boolean isValidPublicEndpoint(HttpServletRequest request) {
        String path = request.getServletPath();
        for (String pattern : PUBLIC_ENDPOINTS) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}

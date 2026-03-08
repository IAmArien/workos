package com.workos.corporate.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;

public interface SecurityEndpoints {
    String[] publicEndpoints();
    boolean isValidPublicEndpoint(HttpServletRequest request);
}

package com.workos.corporate.infrastructure.security;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.presentation.utils.WebTokenUtils;
import com.workos.corporate.shared.response.ApiErrors;
import com.workos.corporate.shared.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;

@Component
public class WebTokenAuthFilter extends OncePerRequestFilter {

    private static final String CODE_UNAUTHORIZED_REQUESTS = "UNREQ001";
    private static final String PUBLIC_PRINCIPAL_TOKEN = "PUBLIC_PRINCIPAL_TOKEN";

    private final WebTokenUtils webTokenUtils;
    private final ObjectMapper objectMapper;

    public WebTokenAuthFilter(WebTokenUtils webTokenUtils, ObjectMapper objectMapper) {
        this.webTokenUtils = webTokenUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        /**
         * If the request URL is a valid public endpoint, we will assign a temporary authentication to the client
         * so the request will proceed as is without the security config blocking the request
         */
        if (webTokenUtils.isValidPublicEndpoint(request)) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(PUBLIC_PRINCIPAL_TOKEN, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        String userId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (webTokenUtils.validateToken(token)) {
                userId = webTokenUtils.extractUserId(token);
            }
        }
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ApiErrors apiErrors = new ApiErrors(
                "Unauthorized request, Invalid/Missing Bearer token", CODE_UNAUTHORIZED_REQUESTS);
            ApiResponse<ApiErrors> apiResponse = ApiResponse.error(HttpResponseStatus.UNAUTHORIZED, apiErrors);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}

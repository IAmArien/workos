package com.workos.corporate.infrastructure.security.filter;

import com.workos.corporate.domain.auth.model.UserCredentials;
import com.workos.corporate.infrastructure.auth.usecases.AuthUseCases;
import com.workos.corporate.infrastructure.security.config.SecurityEndpoints;
import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.presentation.utils.WebTokenUtils;
import com.workos.corporate.shared.response.ApiErrors;
import com.workos.corporate.shared.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String CODE_UNAUTHORIZED_REQUESTS = "UNREQ001";
    private static final String CODE_INVALID_API_KEY = "INVAPIK001";

    @Value("${app.security.api-key}") private String apiKey;

    private final AuthUseCases authUseCases;

    private final ObjectMapper objectMapper;
    private final WebTokenUtils webTokenUtils;
    private final SecurityEndpoints securityEndpoints;

    public AuthenticationFilter(
        AuthUseCases authUseCases,
        ObjectMapper objectMapper,
        WebTokenUtils webTokenUtils,
        SecurityEndpoints securityEndpoints
    ) {
        this.authUseCases = authUseCases;
        this.objectMapper = objectMapper;
        this.webTokenUtils = webTokenUtils;
        this.securityEndpoints = securityEndpoints;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return securityEndpoints.isValidPublicEndpoint(request);
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String apiKey = request.getHeader("X-API-KEY");
        String authHeader = request.getHeader("Authorization");

        if (apiKey == null || !apiKey.equals(this.apiKey)) {
            unauthorized(response, "Invalid/Missing API Key", CODE_INVALID_API_KEY);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorized(response, "Invalid/Missing Bearer token", CODE_UNAUTHORIZED_REQUESTS);
            return;
        }

        String token = authHeader.substring(7);
        if (webTokenUtils.validateToken(token)) {
            String userId = webTokenUtils.extractUserId(token);
            UserCredentials userCredentials = authUseCases.getUserCredentialsByUserId().execute(userId);
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userCredentials.getUserId(),
                    userCredentials,
                    Collections.emptyList()
                );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            return;
        }

        unauthorized(response, "Invalid/Missing Bearer token", CODE_UNAUTHORIZED_REQUESTS);
    }

    private void unauthorized(HttpServletResponse response, String message, String code) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ApiErrors apiErrors = new ApiErrors(message, code);
        ApiResponse<ApiErrors> apiResponse = ApiResponse.error(HttpResponseStatus.UNAUTHORIZED, apiErrors);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}

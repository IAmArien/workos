package com.workos.corporate.infrastructure.security;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.shared.response.ApiErrors;
import com.workos.corporate.shared.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String API_KEY_USER = "api-key-user";
    private static final String CODE_INVALID_API_KEY = "INVAPIK001";
    @Value("${app.security.api-key}") private String apiKey;

    private final ObjectMapper objectMapper;

    public ApiKeyFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestApiKey = request.getHeader(API_KEY_HEADER);
        if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ApiErrors apiErrors = new ApiErrors("Invalid/Missing API Key", CODE_INVALID_API_KEY);
            ApiResponse<ApiErrors> apiResponse = ApiResponse.error(HttpResponseStatus.UNAUTHORIZED, apiErrors);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }
}

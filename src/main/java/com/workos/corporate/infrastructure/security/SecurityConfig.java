package com.workos.corporate.infrastructure.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public interface SecurityConfig {
    PasswordEncoder passwordEncoder();
    SecurityFilterChain securityFilterChain(HttpSecurity http);
}

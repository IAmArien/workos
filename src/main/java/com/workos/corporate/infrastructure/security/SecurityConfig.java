package com.workos.corporate.infrastructure.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface SecurityConfig {
    PasswordEncoder passwordEncoder();
}

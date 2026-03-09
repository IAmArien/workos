package com.workos.corporate.infrastructure.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordConfig {
    PasswordEncoder passwordEncoder();
}

package com.workos.corporate.infrastructure.security.config.impl;

import com.workos.corporate.infrastructure.security.config.PasswordConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfigImpl implements PasswordConfig {

    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

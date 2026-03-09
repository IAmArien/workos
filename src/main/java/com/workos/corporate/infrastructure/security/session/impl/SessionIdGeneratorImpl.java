package com.workos.corporate.infrastructure.security.session.impl;

import com.workos.corporate.infrastructure.security.session.SessionIdGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SessionIdGeneratorImpl implements SessionIdGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generate() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}

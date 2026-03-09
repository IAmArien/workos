package com.workos.corporate.infrastructure.security.impl;

import com.workos.corporate.infrastructure.security.AuthenticationFilter;
import com.workos.corporate.infrastructure.security.SecurityConfig;
import com.workos.corporate.infrastructure.security.SecurityEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigImpl implements SecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final SecurityEndpoints securityEndpoints;

    public SecurityConfigImpl(
        AuthenticationFilter authenticationFilter,
        SecurityEndpoints securityEndpoints
    ) {
        this.authenticationFilter = authenticationFilter;
        this.securityEndpoints = securityEndpoints;
    }

    @Bean
    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(securityEndpoints.publicEndpoints()).permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

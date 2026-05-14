package com.example.exambytel9.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig {
    // diese class ist dafür, um nicht dieses .requestMatchers("/Woche06/secret").hasRole("ADMIN") in SecurityConfig. zu haben
    // sondern im Controller lieber je nach methode mit @secured("ROLE_ADMIN") oder @AdminROle
}

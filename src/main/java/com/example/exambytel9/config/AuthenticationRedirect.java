package com.example.exambytel9.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationRedirect implements AuthenticationSuccessHandler {

    //Wenn Anmeldung erfolgreich ist wende dies an
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        //Je nach Rolle wirst du nach Anmeldung auf die jeweiligen Seiten weitergeleitet
        if (roles.contains("ROLE_STUDENT")) {
            response.sendRedirect("/Student");
        } else if (roles.contains("ROLE_ORGANISATOR")) {
            response.sendRedirect("/Organisator");
        } else if (roles.contains("ROLE_CORRECTOR")) {
            response.sendRedirect("/Korrektor");
        } else {
            response.sendRedirect("/");
        }
    }
}

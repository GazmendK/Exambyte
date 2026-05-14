package com.example.exambytel9.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableConfigurationProperties(RoleConfig.class)
public class SecurityConfiguration {

    //Initianlisierung für den Redirector
    private final AuthenticationSuccessHandler authenticationRedirect;
    private final AppUserService appUserService;

    public SecurityConfiguration(AuthenticationSuccessHandler authenticationRedirect,
                                  AppUserService appUserService) {
        this.authenticationRedirect = authenticationRedirect;
        this.appUserService = appUserService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/", "/login", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/Korrektor/**").hasRole("CORRECTOR")
                        .requestMatchers("/Organisator/**").hasRole("ORGANISATOR")
                        .requestMatchers("/Student/**").hasRole("STUDENT")
                        .anyRequest().authenticated() // --> alle anderen muss man sich anmelden
                )
                .oauth2Login(config -> config
                        .userInfoEndpoint(info -> info.userService(appUserService))
                        .successHandler(authenticationRedirect) //Wenn ein erlaubter Login erfolgt, dann wird die AuthRedirect angewendet
                );

        return http.build();
    }
}

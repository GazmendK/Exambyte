package com.example.exambytel9.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();
    private final RoleConfig roleConfig;

    public AppUserService(RoleConfig roleConfig) {
        this.roleConfig = roleConfig;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User originalUser = defaultService.loadUser(userRequest);

        // GitHub-Benutzername abrufen
        String login = originalUser.getAttribute("login");

        // Überprüfen, ob der Benutzer erlaubt ist
        if (!roleConfig.isAllowed(login)) {
            throw new OAuth2AuthenticationException("Zugriff verweigert für: " + login);
        }

        // Rollen basierend auf dem Benutzer festlegen
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());

        if (roleConfig.getOrganisatoren().contains(login)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANISATOR"));
        } else if (roleConfig.getKorrektoren().contains(login)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CORRECTOR"));
        } else if (roleConfig.getStudenten().contains(login)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "id");
    }
}

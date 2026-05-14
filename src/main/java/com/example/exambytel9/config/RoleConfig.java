package com.example.exambytel9.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.roles")
public class RoleConfig {

    private List<String> organisatoren = new ArrayList<>();
    private List<String> korrektoren = new ArrayList<>();
    private List<String> studenten = new ArrayList<>();

    public List<String> getOrganisatoren() {
        return organisatoren;
    }

    public void setOrganisatoren(List<String> organisatoren) {
        this.organisatoren = organisatoren;
    }

    public List<String> getKorrektoren() {
        return korrektoren;
    }

    public void setKorrektoren(List<String> korrektoren) {
        this.korrektoren = korrektoren;
    }

    public List<String> getStudenten() {
        return studenten;
    }

    public void setStudenten(List<String> studenten) {
        this.studenten = studenten;
    }

    public boolean isAllowed(String login) {
        return organisatoren.contains(login)
                || korrektoren.contains(login)
                || studenten.contains(login);
    }
}

package com.example.exambytel9.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAbgabe {

    private final String studentLogin;
    private final int testId;
    private final LocalDateTime abgabeZeit;
    private final Map<Integer, String> freitextAntworten;
    private final Map<Integer, List<String>> mcAntworten;
    private final Map<Integer, Double> vergebenePunkte;
    private final Map<Integer, String> feedbacks;
    private boolean manuellNichtBestanden;
    private String nichtBestandenBegruendung;

    public TestAbgabe(String studentLogin, int testId,
                      Map<Integer, String> freitextAntworten,
                      Map<Integer, List<String>> mcAntworten) {
        this.studentLogin = studentLogin;
        this.testId = testId;
        this.abgabeZeit = LocalDateTime.now();
        this.freitextAntworten = new HashMap<>(freitextAntworten);
        this.mcAntworten = new HashMap<>(mcAntworten);
        this.vergebenePunkte = new HashMap<>();
        this.feedbacks = new HashMap<>();
    }

    public void setPunkte(int aufgabeId, double punkte) {
        vergebenePunkte.put(aufgabeId, punkte);
    }

    public void setFeedback(int aufgabeId, String feedback) {
        feedbacks.put(aufgabeId, feedback);
    }

    public double getGesamtPunkte() {
        return vergebenePunkte.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public boolean isFreitextKorrigiert(int aufgabeId) {
        return vergebenePunkte.containsKey(aufgabeId);
    }

    public boolean alleFreitextKorrigiert(WochenTest test) {
        return test.getAufgaben().stream()
                .filter(a -> a instanceof Freitext)
                .allMatch(a -> vergebenePunkte.containsKey(a.getId()));
    }

    public String getStudentLogin() {
        return studentLogin;
    }

    public int getTestId() {
        return testId;
    }

    public LocalDateTime getAbgabeZeit() {
        return abgabeZeit;
    }

    public Map<Integer, String> getFreitextAntworten() {
        return freitextAntworten;
    }

    public Map<Integer, List<String>> getMcAntworten() {
        return mcAntworten;
    }

    public Map<Integer, Double> getVergebenePunkte() {
        return vergebenePunkte;
    }

    public Map<Integer, String> getFeedbacks() {
        return feedbacks;
    }

    public boolean isManuellNichtBestanden() {
        return manuellNichtBestanden;
    }

    public void setManuellNichtBestanden(boolean manuellNichtBestanden) {
        this.manuellNichtBestanden = manuellNichtBestanden;
    }

    public String getNichtBestandenBegruendung() {
        return nichtBestandenBegruendung;
    }

    public void setNichtBestandenBegruendung(String nichtBestandenBegruendung) {
        this.nichtBestandenBegruendung = nichtBestandenBegruendung;
    }

    public List<Integer> getUnkorrigierteFreitextIds(WochenTest test) {
        List<Integer> result = new ArrayList<>();
        for (Aufgabe a : test.getAufgaben()) {
            if (a instanceof Freitext && !vergebenePunkte.containsKey(a.getId())) {
                String antwort = freitextAntworten.get(a.getId());
                if (antwort != null && !antwort.isBlank()) {
                    result.add(a.getId());
                }
            }
        }
        return result;
    }
}

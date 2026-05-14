package com.example.exambytel9.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WochenTest {

    private static final double MIN_BESTANDEN_PROZENT = 50.0;

    private int id;
    private String testName;
    private LocalDateTime startZeit;
    private LocalDateTime endZeit;
    private LocalDateTime veroeffentlichung;
    private List<Aufgabe> aufgaben;

    public WochenTest(String testName, LocalDateTime startZeit, LocalDateTime endZeit,
                      LocalDateTime veroeffentlichung, List<Aufgabe> aufgaben, int id) {
        this.testName = testName;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.veroeffentlichung = veroeffentlichung;
        this.aufgaben = aufgaben != null ? new ArrayList<>(aufgaben) : new ArrayList<>();
        this.id = id;
    }

    public WochenTest() {
        this.aufgaben = new ArrayList<>();
    }

    public void addAufgabe(Aufgabe aufgabe) {
        this.aufgaben.add(aufgabe);
    }

    public double getMaxPunkteTest() {
        return aufgaben.stream().mapToDouble(Aufgabe::getMaxPunkte).sum();
    }

    public boolean istGestartet() {
        return LocalDateTime.now().isAfter(startZeit);
    }

    public boolean istBeendet() {
        return LocalDateTime.now().isAfter(endZeit);
    }

    public boolean istVeroeffentlicht() {
        return LocalDateTime.now().isAfter(veroeffentlichung);
    }

    public boolean istBestanden(double erreichtePunkte) {
        double maxPunkte = getMaxPunkteTest();
        if (maxPunkte == 0) {
            return false;
        }
        return erreichtePunkte >= maxPunkte * (MIN_BESTANDEN_PROZENT / 100.0);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDateTime getStartZeit() {
        return startZeit;
    }

    public void setStartZeit(LocalDateTime startZeit) {
        this.startZeit = startZeit;
    }

    public LocalDateTime getEndZeit() {
        return endZeit;
    }

    public void setEndZeit(LocalDateTime endZeit) {
        this.endZeit = endZeit;
    }

    public LocalDateTime getVeroeffentlichung() {
        return veroeffentlichung;
    }

    public void setVeroeffentlichung(LocalDateTime veroeffentlichung) {
        this.veroeffentlichung = veroeffentlichung;
    }

    public List<Aufgabe> getAufgaben() {
        return aufgaben;
    }

    public void setAufgaben(List<Aufgabe> aufgaben) {
        this.aufgaben = new ArrayList<>(aufgaben);
    }
}

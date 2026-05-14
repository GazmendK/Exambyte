package com.example.exambytel9.geschaeftslogik;

public abstract class Aufgabe {

    private boolean testArt;
    private int id;
    private String titel;
    private String beschreibung;
    private double maxPunkte;
    private String erklaerung;

    public Aufgabe(boolean testArt, int id, String titel, String beschreibung,
                   double maxPunkte, String erklaerung) {
        this.testArt = testArt;
        this.id = id;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.maxPunkte = maxPunkte;
        this.erklaerung = erklaerung;
    }

    public Aufgabe() {
    }

    public abstract double berechnePunkte(Object antwort);

    public boolean isTestArt() {
        return testArt;
    }

    public void setTestArt(boolean testArt) {
        this.testArt = testArt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public double getMaxPunkte() {
        return maxPunkte;
    }

    public void setMaxPunkte(double maxPunkte) {
        this.maxPunkte = maxPunkte;
    }

    public String getErklaerung() {
        return erklaerung;
    }

    public void setErklaerung(String erklaerung) {
        this.erklaerung = erklaerung;
    }
}

package com.example.exambytel9.geschaeftslogik;

import java.util.List;

public class MultipleChoiceFormDTO {

    private String titel;
    private String beschreibung;
    private double maxPunkte;
    private String erklaerung;
    private String antwortOptionenText;
    private String richtigeAntwortenText;

    public MultipleChoice toMultipleChoice(int id) {
        List<String> optionen = new TextToMultipleChoice(antwortOptionenText).getMultipleChoice();
        List<String> richtige = new TextToMultipleChoice(richtigeAntwortenText).getMultipleChoice();
        return new MultipleChoice(true, id, titel, beschreibung, maxPunkte, optionen, richtige, erklaerung);
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

    public String getAntwortOptionenText() {
        return antwortOptionenText;
    }

    public void setAntwortOptionenText(String antwortOptionenText) {
        this.antwortOptionenText = antwortOptionenText;
    }

    public String getRichtigeAntwortenText() {
        return richtigeAntwortenText;
    }

    public void setRichtigeAntwortenText(String richtigeAntwortenText) {
        this.richtigeAntwortenText = richtigeAntwortenText;
    }
}

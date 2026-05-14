package com.example.exambytel9.geschaeftslogik;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Aufgabe {

    private List<String> antwortOptionen;
    private List<String> richtigeAntworten;

    public MultipleChoice(boolean testArt, int id, String titel, String beschreibung,
                          double maxPunkte, List<String> antwortOptionen,
                          List<String> richtigeAntworten, String erklaerung) {
        super(true, id, titel, beschreibung, maxPunkte, erklaerung);
        this.antwortOptionen = new ArrayList<>(antwortOptionen);
        this.richtigeAntworten = new ArrayList<>(richtigeAntworten);
    }

    public MultipleChoice() {
        this.antwortOptionen = new ArrayList<>();
        this.richtigeAntworten = new ArrayList<>();
    }

    /**
     * Berechnet Punkte gemäß Spec:
     * - Kein Fehler  → Maximalpunktzahl
     * - Ein Fehler   → Hälfte der Punkte
     * - Mehr Fehler  → 0 Punkte
     *
     * Ein Fehler ist entweder eine falsch ausgewählte Antwort
     * oder eine korrekte Antwort, die nicht ausgewählt wurde.
     */
    @Override
    public double berechnePunkte(Object antwort) {
        if (!(antwort instanceof List)) {
            return 0;
        }

        @SuppressWarnings("unchecked")
        List<String> ausgewaehlt = (List<String>) antwort;

        int fehler = 0;

        for (String gewaehlt : ausgewaehlt) {
            if (!richtigeAntworten.contains(gewaehlt)) {
                fehler++;
            }
        }

        for (String richtig : richtigeAntworten) {
            if (!ausgewaehlt.contains(richtig)) {
                fehler++;
            }
        }

        if (fehler == 0) {
            return getMaxPunkte();
        } else if (fehler == 1) {
            return getMaxPunkte() / 2.0;
        } else {
            return 0;
        }
    }

    public List<String> getAntwortOptionen() {
        return antwortOptionen;
    }

    public void setAntwortOptionen(List<String> antwortOptionen) {
        this.antwortOptionen = new ArrayList<>(antwortOptionen);
    }

    public List<String> getRichtigeAntworten() {
        return richtigeAntworten;
    }

    public void setRichtigeAntworten(List<String> richtigeAntworten) {
        this.richtigeAntworten = new ArrayList<>(richtigeAntworten);
    }
}

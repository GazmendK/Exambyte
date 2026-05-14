package com.example.exambytel9.geschaeftslogik;

import java.util.List;

public class MultipleChoice extends Aufgabe {

    private List<String> antwortOptionen;
    private List<String> richtigeAntworten;

    public MultipleChoice(boolean testArt, int id, String titel, String beschreibung, double maxPunkte, List<String> antwortOptionen, List<String> richtigeAntworten, String erklaerung) {
        super(true, id, titel, beschreibung, maxPunkte, erklaerung);
        this.antwortOptionen = antwortOptionen;
        this.richtigeAntworten = richtigeAntworten;
    }

    public MultipleChoice() {
    }

    @Override
    public double berechnePunkte(Object antwort) {
        if (!(antwort instanceof List)) return 0;

        List<String> ausgewaehlteAntworten = (List<String>) antwort;
        int richtige = 0;
        int falsche = 0;

        for (String a : ausgewaehlteAntworten) {
            if (richtigeAntworten.contains(a)) {
                richtige++;
            } else {
                falsche++;
            }
        }

        double punkteProRichtige = getMaxPunkte() / richtigeAntworten.size();
        double gesamtPunkte = (richtige * punkteProRichtige) - (falsche * punkteProRichtige);

        return Math.max(0, gesamtPunkte);
    }

    public List<String> getAntwortOptionen() {
        return antwortOptionen;
    }

    public List<String> getRichtigeAntworten() {
        return richtigeAntworten;
    }

    public void setAntwortOptionen(List<String> antwortOptionen) {
        this.antwortOptionen = antwortOptionen;
    }

    public void setRichtigeAntworten(List<String> richtigeAntworten) {
        this.richtigeAntworten = richtigeAntworten;
    }
}

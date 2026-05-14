package com.example.exambytel9.geschaeftslogik;

public class  Freitext extends Aufgabe {

    private String loesungsvorschlag;

    public Freitext(boolean testArt, int id, String titel, String beschreibung,
                    double maxPunkte, String loesungsvorschlag) {
        super(false, id, titel, beschreibung, maxPunkte, loesungsvorschlag);
        this.loesungsvorschlag = loesungsvorschlag;
    }

    public Freitext() {
        super();
    }

    /**
     * Freitext wird manuell bewertet – Punkte kommen aus TestAbgabe.
     * Diese Methode gibt 0 zurück; sie dient nur der Erfüllung des Vertrags.
     */
    @Override
    public double berechnePunkte(Object antwort) {
        return 0;
    }

    public String getLoesungsvorschlag() {
        return loesungsvorschlag;
    }

    public void setLoesungsvorschlag(String loesungsvorschlag) {
        this.loesungsvorschlag = loesungsvorschlag;
    }
}

package com.example.exambytel9.pseudoDatenbank;

import com.example.exambytel9.geschaeftslogik.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AbgabeService {

    private final InMemoryAbgabeRepository abgabeRepository;
    private final TestService testService;

    public AbgabeService(InMemoryAbgabeRepository abgabeRepository, TestService testService) {
        this.abgabeRepository = abgabeRepository;
        this.testService = testService;
    }

    public void submitTest(String studentLogin, int testId,
                           Map<Integer, String> freitextAntworten,
                           Map<Integer, List<String>> mcAntworten) {
        WochenTest test = testService.findById(testId);

        if (test.istBeendet()) {
            throw new IllegalStateException("Der Test ist bereits beendet und kann nicht mehr abgegeben werden.");
        }

        TestAbgabe abgabe = new TestAbgabe(studentLogin, testId, freitextAntworten, mcAntworten);

        for (Aufgabe aufgabe : test.getAufgaben()) {
            if (aufgabe instanceof MultipleChoice mc) {
                List<String> selected = mcAntworten.getOrDefault(aufgabe.getId(), List.of());
                double punkte = mc.berechnePunkte(selected);
                abgabe.setPunkte(aufgabe.getId(), punkte);
            }
        }

        abgabeRepository.save(abgabe);
    }

    public Optional<TestAbgabe> findAbgabe(String studentLogin, int testId) {
        return abgabeRepository.findByStudentAndTest(studentLogin, testId);
    }

    public List<TestAbgabe> findAbgabenFuerTest(int testId) {
        return abgabeRepository.findByTestId(testId);
    }

    public List<TestAbgabe> findAbgabenFuerStudent(String studentLogin) {
        return abgabeRepository.findByStudent(studentLogin);
    }

    /**
     * Gibt unkorrigierte Freitext-Abgaben über alle Tests zurück.
     * Format: Map mit Abgabe, WochenTest und Aufgabe.
     */
    public List<FreitextKorrekturInfo> getUnkorrigierteFreitexte() {
        List<FreitextKorrekturInfo> result = new ArrayList<>();
        for (TestAbgabe abgabe : abgabeRepository.findAll()) {
            WochenTest test = testService.findByIdOptional(abgabe.getTestId()).orElse(null);
            if (test == null) continue;
            for (Aufgabe aufgabe : test.getAufgaben()) {
                if (aufgabe instanceof Freitext) {
                    String antwort = abgabe.getFreitextAntworten().get(aufgabe.getId());
                    if (antwort != null && !antwort.isBlank()
                            && !abgabe.isFreitextKorrigiert(aufgabe.getId())) {
                        result.add(new FreitextKorrekturInfo(abgabe, test, (Freitext) aufgabe, antwort));
                    }
                }
            }
        }
        return result;
    }

    public void korrigiereFreitext(String studentLogin, int testId, int aufgabeId,
                                   double punkte, String feedback) {
        TestAbgabe abgabe = abgabeRepository.findByStudentAndTest(studentLogin, testId)
                .orElseThrow(() -> new IllegalArgumentException("Abgabe nicht gefunden"));
        WochenTest test = testService.findById(testId);
        Aufgabe aufgabe = test.getAufgaben().stream()
                .filter(a -> a.getId() == aufgabeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));

        if (punkte < aufgabe.getMaxPunkte() && !freitextAntwortLeer(abgabe, aufgabeId)) {
            if (feedback == null || feedback.isBlank()) {
                throw new IllegalArgumentException(
                        "Nicht-leere Einreichungen, die nicht mit Maximalpunktzahl bewertet werden, benötigen einen Feedbacktext.");
            }
        }

        double begrenzterPunkte = Math.min(punkte, aufgabe.getMaxPunkte());
        abgabe.setPunkte(aufgabeId, begrenzterPunkte);
        if (feedback != null && !feedback.isBlank()) {
            abgabe.setFeedback(aufgabeId, feedback);
        }
    }

    public void markiereNichtBestanden(String studentLogin, int testId, String begruendung) {
        TestAbgabe abgabe = abgabeRepository.findByStudentAndTest(studentLogin, testId)
                .orElseThrow(() -> new IllegalArgumentException("Abgabe nicht gefunden"));
        abgabe.setManuellNichtBestanden(true);
        abgabe.setNichtBestandenBegruendung(begruendung);
    }

    public ZulassungsStatus berechneZulassungsStatus(String studentLogin) {
        List<TestAbgabe> abgaben = abgabeRepository.findByStudent(studentLogin);
        int bewerteteTests = 0;
        int nichtBestanden = 0;

        for (TestAbgabe abgabe : abgaben) {
            WochenTest test = testService.findByIdOptional(abgabe.getTestId()).orElse(null);
            if (test == null || !test.istVeroeffentlicht()) continue;

            bewerteteTests++;

            if (abgabe.isManuellNichtBestanden()
                    || !test.istBestanden(abgabe.getGesamtPunkte())) {
                nichtBestanden++;
            }
        }

        return ZulassungsStatus.berechne(bewerteteTests, nichtBestanden);
    }

    private boolean freitextAntwortLeer(TestAbgabe abgabe, int aufgabeId) {
        String antwort = abgabe.getFreitextAntworten().get(aufgabeId);
        return antwort == null || antwort.isBlank();
    }

    public record FreitextKorrekturInfo(TestAbgabe abgabe, WochenTest test,
                                        Freitext aufgabe, String antwort) {
    }
}

package com.example.exambytel9.geschaeftslogik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestFile {

    private List<WochenTest> testDatenbank;

    public TestFile() {
        testDatenbank = new ArrayList<>();

        List<String> optionen1 = List.of("a) 2", "b) 4", "c) 6", "d) 8");
        List<String> richtige1 = List.of("b");

        List<String> optionen2 = List.of("OOP", "Vererbung", "Polymorphismus", "Kapselung");
        List<String> richtige2 = List.of("Polymorphismus");

        WochenTest wochenTest1 = new WochenTest("Test 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusHours(1), new ArrayList<>(), 1);
        wochenTest1.addAufgabe(new MultipleChoice(true, 1, "Mathe-Frage", "Was ist 2+2?", 5, optionen1, richtige1, "Erklärung zur Frage 1"));
        wochenTest1.addAufgabe(new Freitext(false, 2, "OOP-Frage", "Erkläre, was Polymorphismus ist.", 10, "Erklärung zur Frage 2"));
        wochenTest1.addAufgabe(new MultipleChoice(true, 3, "Mathe-Frage 2", "Was ist 3+3?", 5, List.of("a) 4", "b) 6", "c) 8", "d) 10"), List.of("b"), "Erklärung zur Frage 3"));

        WochenTest wochenTest2 = new WochenTest("Test 2", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusHours(1), new ArrayList<>(), 2);
        wochenTest2.addAufgabe(new MultipleChoice(true, 3, "Noch ein Test", "Welche dieser Begriffe gehören zu OOP?", 5, optionen2, richtige2, "Erklärung zur Frage"));
        wochenTest2.addAufgabe(new Freitext(false, 4, "Frage zu OOP", "Was ist Kapselung?", 5, "Erklärung zur Frage 4"));

        testDatenbank.add(wochenTest1);
        testDatenbank.add(wochenTest2);
    }

    public WochenTest getTestById(int id) {
        for (WochenTest test : testDatenbank) {
            if (test.getId() == id) {
                return test;
            }
        }
        return null;
    }

    public List<WochenTest> getTestDatenbank() {
        return testDatenbank;
    }
}

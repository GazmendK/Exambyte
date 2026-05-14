package com.example.exambytel9.geschaeftslogik;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

class WochenTestTests {

    public ArrayList<Aufgabe> Aufgaben = new ArrayList<Aufgabe>();
    @Test
    void testTest(){
        WochenTest Woche1 = new WochenTest("Test Woche 1", LocalDateTime.now(),
                                            LocalDateTime.now().minusMinutes(30), LocalDateTime.now(), Aufgaben,5);

        System.out.println(Woche1.istBeendet());



    }
}

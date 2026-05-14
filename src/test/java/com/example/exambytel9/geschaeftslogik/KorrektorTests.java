package com.example.exambytel9.geschaeftslogik;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KorrektorTests {

    @Test
    void testGradeTest() {
        Korrektor korrektor = new Korrektor(3L, "korrektor", "korrektor@example.com", "password");
        korrektor.gradeTest(301L, true);
        korrektor.gradeTest(302L, false);

        Map<Long, Boolean> gradedTests = korrektor.getGradedTests();
        assertTrue(gradedTests.get(301L));
        assertFalse(gradedTests.get(302L));
    }

    @Test
    void testHasGradedTest() {
        Korrektor korrektor = new Korrektor(3L, "korrektor", "korrektor@example.com", "password");
        korrektor.gradeTest(301L, true);

        assertTrue(korrektor.hasGradedTest(301L));
        assertFalse(korrektor.hasGradedTest(302L));
    }
}

package com.example.exambytel9.geschaeftslogik;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTests {

    @Test
    void testEnrollInTest() {
        Student student = new Student(1L, "student1", "student1@example.com", "password");
        student.enrollInTest(101L);
        student.enrollInTest(102L);

        List<Long> enrolledTests = student.getEnrolledTests();
        assertTrue(enrolledTests.contains(101L));
        assertTrue(enrolledTests.contains(102L));
    }

    @Test
    void testCompleteTest() {
        Student student = new Student(1L, "student1", "student1@example.com", "password");
        student.completeTest();
        student.completeTest();

        assertEquals(2, student.getCompletedTests());
    }
}

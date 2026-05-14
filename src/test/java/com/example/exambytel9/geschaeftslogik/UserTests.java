package com.example.exambytel9.geschaeftslogik;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    void testAuthenticate() {
        User user = new Student(1L, "studentUser", "student@example.com", "password123");
        assertTrue(user.authenticate("password123"));
        assertFalse(user.authenticate("wrongPassword"));
    }

    @Test
    void testToString() {
        User user = new Organisator(2L, "organisatorUser", "organisator@example.com", "adminpass");
        String expected = "User{id=2, username='organisatorUser', email='organisator@example.com', role='Organisator'}";
        assertEquals(expected, user.toString());
    }

    @Test
    void testRole() {
        User student = new Student(3L, "student", "student@test.com", "pass");
        User organisator = new Organisator(4L, "organisator", "organisator@test.com", "pass");
        User korrektor = new Korrektor(5L, "korrektor", "korrektor@test.com", "pass");

        assertEquals("Student", student.getRole());
        assertEquals("Organisator", organisator.getRole());
        assertEquals("Korrektor", korrektor.getRole());
    }
}

package com.example.exambytel9.geschaeftslogik;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganisatorTests {

    @Test
    void testCreateTest() {
        Organisator organisator = new Organisator(2L, "organisator", "organisator@example.com", "password");
        organisator.createTest(201L);
        organisator.createTest(202L);

        List<Long> createdTests = organisator.getCreatedTests();
        assertTrue(createdTests.contains(201L));
        assertTrue(createdTests.contains(202L));
    }

    @Test
    void testRemoveTest() {
        Organisator organisator = new Organisator(2L, "organisator", "organisator@example.com", "password");
        organisator.createTest(201L);
        organisator.removeTest(201L);

        List<Long> createdTests = organisator.getCreatedTests();
        assertFalse(createdTests.contains(201L));
    }
}
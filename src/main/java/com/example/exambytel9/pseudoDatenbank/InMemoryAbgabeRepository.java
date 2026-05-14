package com.example.exambytel9.pseudoDatenbank;

import com.example.exambytel9.geschaeftslogik.TestAbgabe;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAbgabeRepository {

    private final List<TestAbgabe> abgaben = new ArrayList<>();

    public synchronized void save(TestAbgabe abgabe) {
        abgaben.removeIf(a -> a.getStudentLogin().equals(abgabe.getStudentLogin())
                && a.getTestId() == abgabe.getTestId());
        abgaben.add(abgabe);
    }

    public Optional<TestAbgabe> findByStudentAndTest(String studentLogin, int testId) {
        return abgaben.stream()
                .filter(a -> a.getStudentLogin().equals(studentLogin) && a.getTestId() == testId)
                .findFirst();
    }

    public List<TestAbgabe> findByTestId(int testId) {
        return abgaben.stream()
                .filter(a -> a.getTestId() == testId)
                .toList();
    }

    public List<TestAbgabe> findByStudent(String studentLogin) {
        return abgaben.stream()
                .filter(a -> a.getStudentLogin().equals(studentLogin))
                .toList();
    }

    public List<TestAbgabe> findAll() {
        return new ArrayList<>(abgaben);
    }
}

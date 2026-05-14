package com.example.exambytel9.pseudoDatenbank;

import com.example.exambytel9.geschaeftslogik.WochenTest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryTestRepository {

    private final List<WochenTest> tests = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public synchronized WochenTest save(WochenTest test) {
        test.setId(idGenerator.getAndIncrement());
        tests.add(test);
        return test;
    }

    public Optional<WochenTest> findById(Integer id) {
        return tests.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public void deleteById(Integer id) {
        tests.removeIf(t -> t.getId().equals(id));
    }

    public List<WochenTest> findAll() {
        return new ArrayList<>(tests);
    }
}

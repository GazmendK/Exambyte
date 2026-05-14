package com.example.exambytel9.pseudoDatenbank;

import com.example.exambytel9.geschaeftslogik.WochenTest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final InMemoryTestRepository testRepository;

    public TestService(InMemoryTestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public WochenTest saveTest(WochenTest test) {
        return testRepository.save(test);
    }

    public WochenTest findById(Integer id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test nicht gefunden: " + id));
    }

    public Optional<WochenTest> findByIdOptional(Integer id) {
        return testRepository.findById(id);
    }

    public void deleteTest(Integer id) {
        testRepository.deleteById(id);
    }

    public List<WochenTest> getTests() {
        return testRepository.findAll();
    }
}

package com.example.exambytel9.geschaeftslogik;

import java.util.ArrayList;
import java.util.List;

public class TextToMultipleChoice {

    private final String multipleChoice;

    public TextToMultipleChoice(String multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public List<String> getMultipleChoice() {
        List<String> listChoices = new ArrayList<>();
        if (multipleChoice == null || multipleChoice.isBlank()) {
            return listChoices;
        }
        for (String item : multipleChoice.split(",")) {
            String trimmed = item.trim();
            if (!trimmed.isEmpty()) {
                listChoices.add(trimmed);
            }
        }
        return listChoices;
    }
}

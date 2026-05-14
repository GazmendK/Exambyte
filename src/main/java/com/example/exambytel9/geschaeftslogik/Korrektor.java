package com.example.exambytel9.geschaeftslogik;

public class Korrektor extends User {

    public Korrektor(Long id, String username, String email) {
        super(id, username, email);
    }

    @Override
    public String getRole() {
        return "Korrektor";
    }
}

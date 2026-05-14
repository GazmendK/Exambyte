package com.example.exambytel9.geschaeftslogik;

public class Organisator extends User {

    public Organisator(Long id, String username, String email) {
        super(id, username, email);
    }

    @Override
    public String getRole() {
        return "Organisator";
    }
}

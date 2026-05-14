package com.example.exambytel9.geschaeftslogik;

public class Student extends User {

    public Student(Long id, String username, String email) {
        super(id, username, email);
    }

    @Override
    public String getRole() {
        return "Student";
    }
}

package com.example.studentinfo;

public class User {
    private String Fullname;

    public User(String fullname) {
        Fullname = fullname;
    }

    public User() {
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }
}

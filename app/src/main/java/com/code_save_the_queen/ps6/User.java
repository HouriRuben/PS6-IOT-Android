package com.code_save_the_queen.ps6;

public class User {
    public String mail;
    public String password;

    public User(String email, String password) {
        this.mail = email;
        this.password = password;
    }

    public String getEmail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}

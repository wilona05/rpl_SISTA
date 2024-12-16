package com.example.sista.Users;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private String noInduk;
    private String nama;
    private String email;
    private String passwords;
    private String role;

    public User(String email, String passwords) {
        this.email = email;
        this.passwords = passwords;
    }

    public User(String noInduk, String email, String role) {
        this.noInduk = noInduk;
        this.email = email;
        this.role = role;
    }

    public User(String nama) {
        this.nama = nama;
    }
}

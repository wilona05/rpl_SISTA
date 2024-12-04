package com.example.sista.Users;

import java.util.List;

public interface UserRepository {
    String login(String email, String password);
    List<User> findAll();
    User getUserByID(String noInduk);
    boolean register(User user);
}

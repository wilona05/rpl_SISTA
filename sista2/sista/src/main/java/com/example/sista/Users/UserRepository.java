package com.example.sista.Users;

import java.util.List;

public interface UserRepository {
    String login(String email, String password);

    List<User> findAll();

    List<User> findAllDosen();

    User getUserByID(String noInduk);

    boolean getStatusKoordinator(String email);

    boolean register(User user);

    boolean editRoleDosen(String noInduk, String newRole);
}

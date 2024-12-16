package com.example.sista.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JdbcUserRepository repo;

    public List<User> getAllDosen() {
        return repo.findAllDosen();
    }
}
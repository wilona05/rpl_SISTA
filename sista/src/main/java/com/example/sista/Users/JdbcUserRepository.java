package com.example.sista.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String login(String email, String passwords){
        String sql = "SELECT email, passwords FROM listUser WHERE email=? AND passwords=?";
        try{
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email, passwords}, this::mapRowToUserLogin);
            return user.getEmail();
        }catch (EmptyResultDataAccessException e){ //user tidak ditemukan
            return null;
        }
    }

    private User mapRowToUserLogin(ResultSet resultSet, int rowNum) throws SQLException{
        return new User(
            resultSet.getString("email"),
            resultSet.getString("passwords")
        );
    }

    @Override
    public List<User> findAll(){
        String sql = "SELECT * FROM listUser";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException{
        String email = resultSet.getString("email");
        return new User(
            resultSet.getString("noInduk"),
            resultSet.getString("nama"),
            email,
            resultSet.getString("passwords"),
            role(email)
        );
    }

    private String role(String email){
        if(email.endsWith("@student.edu")){
            return "mahasiswa";
        }else if(email.endsWith("@dosen.edu")){
            return "dosen";
        }else{
            return "admin";
        }
    }

    @Override
    public User getUserByID(String noInduk){
        String sql = "SELECT * FROM listUser WHERE noInduk=?";
        try{
            User user = jdbcTemplate.queryForObject(sql, new Object[]{noInduk}, this::mapRowToUser);
            return user;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
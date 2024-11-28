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
        String sql = "SELECT * FROM listUser WHERE email=? AND passwords=?";
        try{
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email, passwords}, this::mapRowToUser);
            return user.getEmail();
        }catch (EmptyResultDataAccessException e){ //user tidak ditemukan
            return null;
        }
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException{
        return new User(
            resultSet.getString("email"),
            resultSet.getString("passwords")
        );
    }
}

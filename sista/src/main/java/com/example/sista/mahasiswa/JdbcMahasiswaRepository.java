package com.example.sista.mahasiswa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMahasiswaRepository implements MahasiswaRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getNpmMahasiswa(String email){
        String sql = "SELECT npm FROM mahasiswa WHERE email = ?";

        if (email == null || email.isEmpty()) {
            return null;
        }

        try {
            return jdbcTemplate.queryForObject(sql, String.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


}
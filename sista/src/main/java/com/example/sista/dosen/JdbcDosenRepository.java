package com.example.sista.dosen;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDosenRepository implements DosenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean checkStatusKoord(String email) {
        // String email = (String) httpSession.getAttribute("email");
        String sql = "SELECT statuskoordinator FROM dosen WHERE email = ?";

        if (email == null || email.isEmpty()) {
            return false;
        }

        try {
            // Execute the query and return the result as a Boolean
            return jdbcTemplate.queryForObject(sql, Boolean.class, email);
        } catch (EmptyResultDataAccessException e) {
            // Handle the case where no record is found
            return false;
        }
    }

    @Override
    public String getNipDosen(String email) {
        String sql = "SELECT nip FROM dosen WHERE email = ?";

        if (email == null || email.isEmpty()) {
            return null;
        }

        try {
            return jdbcTemplate.queryForObject(sql, String.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void inputRevisi(int idSidang, String revisi) {
        jdbcTemplate.update("UPDATE sidangTA SET catatanrevisi = ? WHERE idSidang = ?", revisi, idSidang);
    }

}

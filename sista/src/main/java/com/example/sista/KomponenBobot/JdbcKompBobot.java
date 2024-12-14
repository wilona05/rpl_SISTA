package com.example.sista.KomponenBobot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;

@Repository
public class JdbcKompBobot implements KompBobotRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Override
    public List<KomponenNilai> findAll() {
        String sql = "SELECT komponen, bobotPenguji, bobotPembimbing FROM komponennilai";
        return jdbcTemplate.query(sql, this::mapRowToBobotNilai);
    }

    private KomponenNilai mapRowToBobotNilai(ResultSet resultSet, int rowNum) throws SQLException {
        return new KomponenNilai(
            resultSet.getString("komponen"), 
            resultSet.getString("bobotPenguji"), 
            resultSet.getString("bobotPembimbing")
        );
    }
}

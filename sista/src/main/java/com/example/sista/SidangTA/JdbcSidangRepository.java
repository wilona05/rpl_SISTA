package com.example.sista.SidangTA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;

@Repository
public class JdbcSidangRepository implements SidangRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SidangTA> findAll() {
        String sql = "SELECT * FROM listSidang";
        return jdbcTemplate.query(sql, this::mapRowToSidang);
    }

    private SidangTA mapRowToSidang(ResultSet resultSet, int rowNum) throws SQLException {
        return new SidangTA(resultSet.getString("namaPeserta"), resultSet.getTimestamp("jadwal"));
    }

    @Override
    public boolean registerSidang(SidangTA sidangTA) {
        return true;
    }

    @Override
    public SidangTA getSidangByNama(String nama) {
        String sql = "SELECT * FROM listSidang WHERE nama=?";
        try {
            SidangTA sidangTA = jdbcTemplate.queryForObject(sql, new Object[] { nama }, this::mapRowToSidang);
            return sidangTA;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
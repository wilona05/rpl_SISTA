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
        String sql = "";
        int rowsAffected = 0;
        String semester = "";
        /*
         * ganjil: sep-okt-nov-des-jan ; genap: feb-mar-apr-jun-jul
         * 9-10-11-12-1
         */
        int month = sidangTA.getJadwal().getMonth();
        if ((month >= 9 && month <= 12) || month == 1) {
            semester = "Ganjil";
        } else {
            semester = "Genap";
        }

        int year = sidangTA.getJadwal().getYear();
        String tahunAjaran = "";
        tahunAjaran += year + "/" + (year + 1);
        String catatanRevisi = "";

        try {
            sql = "INSERT INTO sidangta (jenista, judulta, jadwal, tempat, semester, tahunajaran, catatanrevisi, npm, nilaikoordinator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            rowsAffected = jdbcTemplate.update(sql, sidangTA.getJenisTA(), sidangTA.getJudulTA(), sidangTA.getJadwal(),
                    sidangTA.getTempat(), semester, tahunAjaran, catatanRevisi, sidangTA.getNpm(), 100.00);
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
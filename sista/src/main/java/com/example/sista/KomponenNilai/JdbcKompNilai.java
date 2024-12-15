package com.example.sista.KomponenNilai;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;

@Repository
public class JdbcKompNilai implements KompNilaiRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Override
    public List<KomponenNilai> findAll() {
        String sql = "SELECT idKomp, komponen, bobotPenguji, bobotPembimbing FROM komponennilai";
        return jdbcTemplate.query(sql, this::mapRowToBobotNilai);
    }

    private KomponenNilai mapRowToBobotNilai(ResultSet resultSet, int rowNum) throws SQLException {
        return new KomponenNilai(
            resultSet.getInt("idKomp"), 
            resultSet.getString("komponen"), 
            resultSet.getBigDecimal("bobotPenguji"), 
            resultSet.getBigDecimal("bobotPembimbing")
        );
    }

    @Override
    public void addKomponenNilai(String komponen, BigDecimal bobotPenguji, BigDecimal bobotPembimbing){
        String sql = "INSERT INTO komponenNilai (komponen, bobotPenguji, bobotPembimbing) VALUES (?,?,?)";
        jdbcTemplate.update(sql, komponen, bobotPenguji, bobotPembimbing);
    }

    @Override   
    public boolean deleteKomponenNilai(Integer idKomp){
        String sql = "DELETE FROM komponenNilai WHERE idKomp=?";
        try{
            jdbcTemplate.update(sql, idKomp);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public void updateKomponenNilai(Integer idKomp, String komponen, BigDecimal bobotPenguji, BigDecimal bobotPembimbing){
        String sql = "UPDATE komponenNilai SET komponen=?, bobotPenguji=?, bobotPembimbing=? WHERE idKomp=?";
        jdbcTemplate.update(sql, komponen, bobotPenguji, bobotPembimbing, idKomp);
    }
}

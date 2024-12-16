package com.example.sista.InputNilai;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcInputNilaiRepository implements InputNilaiRepository{
 
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getNamaRole(Integer idSidang, String nip){
        String sql = "SELECT namaRole FROM cekRoleDosen WHERE idSidang=? AND nip=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idSidang, nip}, String.class);
    }

    @Override
    public List<Nilai> getNilai(Integer idSidang, String namaRole){
        String sql = "SELECT idSidang, idKomp, komponen, bobot, nilai FROM nilaiDosen WHERE idSidang = ? AND namaRole = ?";
        return jdbcTemplate.query(sql, new Object[]{idSidang, namaRole}, this::mapRowToNilai);
    }

    private Nilai mapRowToNilai(ResultSet resultSet, int rowNum) throws SQLException{
        return new Nilai(
            resultSet.getInt("idSidang"),
            resultSet.getInt("idKomp"),
            resultSet.getString("komponen"),
            resultSet.getBigDecimal("bobot"), 
            resultSet.getBigDecimal("nilai")
        );
    }

    @Override
    public boolean inputNilai(Integer idSidang, Integer idKomp, BigDecimal nilai, String namaRole){
        String sql = "";
        switch(namaRole){
            case "Penguji 1":
                sql = "UPDATE nilaiSidang SET nilaiPenguji1=? WHERE idSidang=? AND idKomp=?";
                break;
            case "Penguji 2":
                sql = "UPDATE nilaiSidang SET nilaiPenguji2=? WHERE idSidang=? AND idKomp=?";
                break;
            case "Pembimbing 1":
            case "Pembimbing 2":
                sql = "UPDATE nilaiSidang SET nilaiPembimbing=? WHERE idSidang=? AND idKomp=?";
                break;
            default:
                throw new IllegalArgumentException("Role tidak valid: " + namaRole);
        }
        try{
            jdbcTemplate.update(sql, nilai, idSidang, idKomp);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

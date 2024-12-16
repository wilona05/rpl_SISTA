package com.example.sista.InputNilai;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcNilaiRepository implements NilaiRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getNamaRole(Integer idSidang, String nip) {
        String sql = "SELECT namaRole FROM cekRoleDosen WHERE idSidang=? AND nip=?";
        return jdbcTemplate.queryForObject(sql, new Object[] { idSidang, nip }, String.class);
    }

    @Override
    public List<Nilai> getNilai(Integer idSidang, String namaRole) {
        String sql = "SELECT * FROM nilaiDosen WHERE idSidang = ? AND namaRole = ?";
        return jdbcTemplate.query(sql, new Object[] { idSidang, namaRole }, this::mapRowToNilai);
    }

    private Nilai mapRowToNilai(ResultSet resultSet, int rowNum) throws SQLException {
        BigDecimal nilaiAkhir = (resultSet.getBigDecimal("bobot"))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(resultSet.getBigDecimal("nilai"))
                .setScale(2,
                        RoundingMode.HALF_UP);
        return new Nilai(
                resultSet.getInt("idSidang"),
                resultSet.getInt("idKomp"),
                resultSet.getString("namarole"),
                resultSet.getString("komponen"),
                resultSet.getBigDecimal("bobot"),
                resultSet.getBigDecimal("nilai"),
                nilaiAkhir);
    }

    private NilaiAkhir mapRowToNilaiAkhir(ResultSet resultSet, int rowNum) throws SQLException {
        return new NilaiAkhir(
                resultSet.getInt("idSidang"),
                resultSet.getString("namaRole"),
                resultSet.getBigDecimal("nilai"),
                resultSet.getBigDecimal("bobot"),
                resultSet.getBigDecimal("nilaiAkhir"));
    }

    @Override
    public boolean inputNilai(Integer idSidang, Integer idKomp, BigDecimal nilai, String namaRole) {
        String sql = "";
        switch (namaRole) {
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
        try {
            jdbcTemplate.update(sql, nilai, idSidang, idKomp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Nilai> getAllNilai(Integer idSidang) {
        String sql = "SELECT * FROM nilaidosen WHERE idsidang = ?";
        return jdbcTemplate.query(sql, new Object[] { idSidang }, this::mapRowToNilai);
    }

    // @Override
    // public BigDecimal calculateTotalNilai(List<Nilai> listNilai) {
    // return listNilai.stream().map(Nilai::getNilaiAkhir).reduce(BigDecimal.ZERO,
    // BigDecimal::add);
    // }

    @Override
    public List<NilaiAkhir> hasilNilaiAkhir(Integer idSidang) {
        String sql = "SELECT * FROM nilaiakhir WHERE idSidang = ?";
        return jdbcTemplate.query(sql, new Object[] { idSidang }, this::mapRowToNilaiAkhir);
    }

    @Override
    public BigDecimal totalNilai(Integer idSidang) {
        String sql = "SELECT SUM(nilaiakhir) AS totalNilai FROM nilaiakhir WHERE idsidang = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { idSidang }, BigDecimal.class);
    }
}
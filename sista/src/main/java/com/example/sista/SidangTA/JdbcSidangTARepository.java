package com.example.sista.SidangTA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.sista.Dosen.JdbcDosenRepository;

@Repository
public class JdbcSidangTARepository implements SidangTARepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcDosenRepository repoDosen;

    @Override
    public List<SidangTA> findAll() {
        String sql = "SELECT * FROM sidangta";
        return jdbcTemplate.query(sql, this::mapRowToSidang);
    }

    // public SidangTA mapRowToSidang(ResultSet rs, int rowNum) throws SQLException
    // {
    // return new SidangTA(rs.getString("nama"), rs.getString("npm"),
    // rs.getString("email"), rs.getString("judulta"),
    // rs.getInt("jenista"), rs.getTimestamp("jadwal"), rs.getString("tempat"),
    // rs.getString("semester"),
    // rs.getString("dosenpembimbing"), rs.getString("ketuapenguji"),
    // rs.getString("anggotapenguji"));
    // }

    public SidangTA mapRowToSidang(ResultSet rs, int rowNum) throws SQLException {
        return new SidangTA(rs.getString("nama"), rs.getTimestamp("jadwal"));
    }

    @Override
    public boolean registerSidang(SidangTA sidangTA) {
        String sqlSidang = "INSERT INTO sidangta (jenista, judulta, jadwal, tempat, semester, tahunajaran, catatanrevisi, npm, nilaikoordinator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDosen = "INSERT INTO dosensidang (idsidang, nip, idrole) VALUES (?, ?, ?)";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlSidang, new String[] { "idsidang" });
                ps.setInt(1, sidangTA.getJenisTA());
                ps.setString(2, sidangTA.getJudulTA());
                ps.setTimestamp(3, sidangTA.getJadwal());
                ps.setString(4, sidangTA.getTempat());
                ps.setString(5, sidangTA.getSemester());
                ps.setString(6, sidangTA.getTahunAjaran());
                ps.setString(7, sidangTA.getCatatanRevisi());
                ps.setString(8, sidangTA.getNpm());
                ps.setInt(9, 100);
                return ps;
            }, keyHolder);
            int idsidang = keyHolder.getKey().intValue();

            String[] dosen = new String[] { sidangTA.getDosenPembimbing1(), sidangTA.getDosenPembimbing2(),
                    sidangTA.getDosenPenguji1(), sidangTA.getDosenPenguji2() };
            for (int i = 0; i < dosen.length; i++) {
                if (dosen[i] != null && !dosen[i].isEmpty() && !dosen[i].equalsIgnoreCase("null")) {
                    String nip = repoDosen.getNipDosenbyNama(dosen[i]);
                    jdbcTemplate.update(sqlDosen, idsidang, nip, i + 2);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SidangTA getSidangByNama(String nama) {
        String sql = "SELECT * FROM listSidang WHERE nama = ?";
        try {
            SidangTA sidangTA = jdbcTemplate.queryForObject(sql, new Object[] { nama }, this::mapRowToSidang);
            return sidangTA;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SidangTA> getAllSidang() {
        String sql = "SELECT * FROM mahasiswasidang";
        return jdbcTemplate.query(sql, this::mapRowToSidang);
    }

    @Override
    public List<SidangTA> getSidangByDosen(String nip) {
        String sql = "SELECT * FROM listSidang WHERE nip = ?";
        return jdbcTemplate.query(sql, this::mapRowToSidang, nip);
    }

    public List<SidangTA> findByRole(int idrole, String nip) {
        String sql = "";
        if (idrole == 1) {
            sql = "SELECT * FROM sidangpenguji WHERE nip = ?";
        } else {
            sql = "SELECT * FROM sidangpembimbing WHERE nip = ?";
        }

        return jdbcTemplate.query(sql, this::mapRowToSidang, nip);
    }

    public List<SidangTA> getSidangItemsByRole(int idrole, String nip) {
        if (idrole == 1) { // jika role penguji
            return findByRole(1, nip);
        } else if (idrole == 2) { // jika role pembimbing
            return findByRole(2, nip);
        } else {
            return Collections.emptyList();
        }
    }

    public InfoSidang mapRowToInfoSidang(ResultSet rs, int rowNum) throws SQLException {
        return new InfoSidang(
                rs.getString("nama"),
                rs.getString("npm"),
                rs.getInt("idsidang"),
                rs.getString("judulTA"),
                rs.getInt("jenisTA"),
                rs.getTimestamp("jadwal"),
                rs.getString("tempat"),
                rs.getString("semester"),
                rs.getString("tahunAjaran"),
                rs.getString("catatanRevisi"),
                rs.getString("pembimbingUtama"),
                rs.getString("pembimbingTambahan"),
                rs.getString("ketuaPenguji"),
                rs.getString("anggotaPenguji"));
    }

    @Override
    public List<InfoSidang> getInfoSidangById(int id) {
        String sql = "SELECT * FROM infosidang WHERE idsidang = ?";
        return jdbcTemplate.query(sql, this::mapRowToInfoSidang, id);
    }
}
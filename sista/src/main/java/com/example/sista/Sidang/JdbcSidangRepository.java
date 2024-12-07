package com.example.sista.Sidang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcSidangRepository implements SidangRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Sidang mapRowToSidang(ResultSet rs, int rowNum) throws SQLException {
        return new Sidang(
                rs.getInt("idsidang"),
                rs.getInt("jenita"),
                rs.getString("judulta"),
                rs.getDate("jadwal"),
                rs.getString("tempat"),
                rs.getString("semester"),
                rs.getString("tahunajaran"),
                rs.getString("catatanrevisi"),
                rs.getString("npm"),
                rs.getInt("nilaikoordinator")
        );
    }

    public List<Sidang> findByRole(int idrole, String nip) {
        String sql = "";
        if (idrole == 1) {
            sql = "SELECT sidangta.* " +
                    "FROM sidangta " +
                    "JOIN dosensidang ON sidangta.idsidang = dosensidang.idsidang " +
                    "WHERE dosensidang.nip = 2 OR dosensidang.nip = 3  AND dosensidang.role = ?";
        }else{
            sql = "SELECT sidangta.* " +
                    "FROM sidangta " +
                    "JOIN dosensidang ON sidangta.idsidang = dosensidang.idsidang " +
                    "WHERE dosensidang.nip = 4 OR dosensidang.nip = 5  AND dosensidang.role = ?";
        }

        return jdbcTemplate.query(sql, this::mapRowToSidang, nip, idrole);
    }

    @Override
    public List<Sidang> getAllSidang() {
        String sql = "SELECT * FROM sidang";
        return jdbcTemplate.query(sql, this::mapRowToSidang);
    }

    public List<Sidang> getSidangItemsByRole(int idrole, String nip) {
        if (idrole == 2 || idrole == 3) { //jika role penguji
            return findByRole(1, nip);
        } else if (idrole == 4 || idrole == 5) { //jika role pembimbing
            return findByRole(2, nip);
        } else {
            return Collections.emptyList();
        }
    }
}

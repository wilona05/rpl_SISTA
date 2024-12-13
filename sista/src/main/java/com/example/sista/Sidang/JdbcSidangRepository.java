package com.example.sista.Sidang;

import com.example.sista.dosen.JdbcDosenRepository;
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

    @Autowired
    JdbcDosenRepository dosenRepo;

    public Sidang mapRowToSidang(ResultSet rs, int rowNum) throws SQLException {
        return new Sidang(
                rs.getString("nama"),
                rs.getInt("idsidang"),
                rs.getInt("jenista"),
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
        //jika dosen penguji
        if (idrole == 1) {
            sql = "SELECT sidangta.*, mahasiswa.nama " +
                    "FROM sidangta " +
                    "JOIN dosensidang ON sidangta.idsidang = dosensidang.idsidang " +
                    "JOIN mahasiswa ON sidangta.npm = mahasiswa.npm " +
                    "WHERE (dosensidang.idrole = 2 OR dosensidang.idrole = 3) AND dosensidang.nip = ?";
//            Jika disen pembimbing
        }else{
            sql = "SELECT sidangta.*, mahasiswa.nama " +
                    "FROM sidangta " +
                    "JOIN dosensidang ON sidangta.idsidang = dosensidang.idsidang " +
                    "JOIN mahasiswa ON sidangta.npm = mahasiswa.npm " +
                    "WHERE (dosensidang.idrole = 4 OR dosensidang.idrole = 5) AND dosensidang.nip = ?";
        }

        return jdbcTemplate.query(sql, this::mapRowToSidang, nip);
    }

    @Override
    public List<Sidang> getAllSidang() {
        String sql = "SELECT \n" +
                "    s.*,\n" +
                "    m.nama \n" +
                "FROM \n" +
                "    Sidangta s\n" +
                "JOIN \n" +
                "    mahasiswa m ON s.npm = m.npm;";
        return jdbcTemplate.query(sql, this::mapRowToSidang);
    }

    @Override
    public List<Sidang> getSidangByDosen(String nip){
        String sql = "SELECT\n" +
                "    s.idsidang,\n" +
                "    m.nama,\n" +
                "    s.jenista,\n" +
                "    s.judulta,\n" +
                "    s.jadwal,\n" +
                "    s.tempat,\n" +
                "    s.semester,\n" +
                "    s.tahunajaran,\n" +
                "    s.catatanrevisi,\n" +
                "    s.npm,\n" +
                "    s.nilaikoordinator\n" +
                "FROM\n" +
                "    sidangta s\n" +
                "JOIN\n" +
                "    dosensidang ds ON s.idsidang = ds.idsidang\n" +
                "JOIN\n" +
                "    mahasiswa m ON s.npm = m.npm\n" +
                "WHERE\n" +
                "    ds.nip = ?";
        return jdbcTemplate.query(sql,this::mapRowToSidang, nip);
    }

    public List<Sidang> getSidangItemsByRole(int idrole, String nip) {
        if (idrole == 1) { //jika role penguji
            return findByRole(1, nip);
        } else if (idrole == 2) { //jika role pembimbing
            return findByRole(2, nip);
        } else {
            return Collections.emptyList();
        }
    }

    public InfoSidang mapRowToInfoSidang(ResultSet rs, int rowNum)throws SQLException{
        return new InfoSidang(
            rs.getString("namamahasiswa"),
            rs.getString("email"),
            rs.getInt("idsidang"),
            rs.getInt("jenista"),
            rs.getString("judulta"),
            rs.getDate("jadwal"),
            rs.getString("tempat"),
            rs.getString("semester"),
            rs.getString("tahunajaran"),
            rs.getString("catatanrevisi"),
            rs.getString("npm"),
            rs.getString("pembimbingutama"),
            rs.getString("pembimbingtambahan"),
            rs.getString("ketuapenguji"),
            rs.getString("anggotapenguji")
        );
    }

    @Override
    public List<InfoSidang> getInfoSidangById(int id){
        String sql = "    SELECT\n" +
                "        m.nama AS namaMahasiswa,\n" +
                "        m.NPM,\n" +
                "        m.email,\n" +
                "        s.idsidang,\n" +
                "        s.judulTA,\n" +
                "        s.jenisTA,\n" +
                "        s.jadwal,\n" +
                "        s.tempat,\n" +
                "        s.semester,\n" +
                "        s.tahunAjaran,\n" +
                "        s.catatanrevisi,\n" +
                "        COALESCE(dPembimbingUtama.nama, '-') AS pembimbingUtama,\n" +
                "        COALESCE(dPembimbingTambahan.nama, '-') AS pembimbingTambahan,\n" +
                "        COALESCE(dKetuaPenguji.nama, '-') AS ketuaPenguji,\n" +
                "        COALESCE(dAnggotaPenguji.nama, '-') AS anggotaPenguji\n" +
                "    FROM\n" +
                "        SidangTA s\n" +
                "            JOIN\n" +
                "        Mahasiswa m ON s.NPM = m.NPM\n" +
                "            LEFT JOIN\n" +
                "        DosenSidang dsPembimbingUtama ON s.IDSidang = dsPembimbingUtama.idSidang AND dsPembimbingUtama.idRole = (\n" +
                "            SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 1'\n" +
                "        )\n" +
                "            LEFT JOIN\n" +
                "        Dosen dPembimbingUtama ON dsPembimbingUtama.NIP = dPembimbingUtama.NIP\n" +
                "            LEFT JOIN\n" +
                "        DosenSidang dsPembimbingTambahan ON s.IDSidang = dsPembimbingTambahan.idSidang AND dsPembimbingTambahan.idRole = (\n" +
                "            SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 2'\n" +
                "        )\n" +
                "            LEFT JOIN\n" +
                "        Dosen dPembimbingTambahan ON dsPembimbingTambahan.NIP = dPembimbingTambahan.NIP\n" +
                "            LEFT JOIN\n" +
                "        DosenSidang dsKetuaPenguji ON s.IDSidang = dsKetuaPenguji.idSidang AND dsKetuaPenguji.idRole = (\n" +
                "            SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 1'\n" +
                "        )\n" +
                "            LEFT JOIN\n" +
                "        Dosen dKetuaPenguji ON dsKetuaPenguji.NIP = dKetuaPenguji.NIP\n" +
                "            LEFT JOIN\n" +
                "        DosenSidang dsAnggotaPenguji ON s.IDSidang = dsAnggotaPenguji.idSidang AND dsAnggotaPenguji.idRole = (\n" +
                "            SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 2'\n" +
                "        )\n" +
                "            LEFT JOIN\n" +
                "        Dosen dAnggotaPenguji ON dsAnggotaPenguji.NIP = dAnggotaPenguji.NIP\n" +
                "    WHERE\n" +
                "        s.idSidang=?";
        return jdbcTemplate.query(sql, this::mapRowToInfoSidang, id);

    }
}

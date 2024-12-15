package com.example.sista.BAP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class bapService implements bapRepo {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public isiBAP findIsiBAP(int idSidang) {
        String query = "SELECT s.semester, s.tahunajaran, s.jenisTA, m.npm, m.nama, s.judulta, "
                + "COALESCE(dPembimbingUtama.nama, '-') AS dosbim1, "
                + "COALESCE(dPembimbingTambahan.nama, '-') AS dosbim2, "
                + "COALESCE(dKetuaPenguji.nama, '-') AS dosji1, "
                + "COALESCE(dAnggotaPenguji.nama, '-') AS dosji2, "
                + "s.jadwal AS tanggalsidang "
                + "FROM SidangTA s "
                + "JOIN Mahasiswa m ON s.NPM = m.NPM "
                + "LEFT JOIN DosenSidang dsPembimbingUtama ON s.IDSidang = dsPembimbingUtama.idSidang "
                + "AND dsPembimbingUtama.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 1') "
                + "LEFT JOIN Dosen dPembimbingUtama ON dsPembimbingUtama.NIP = dPembimbingUtama.NIP "
                + "LEFT JOIN DosenSidang dsPembimbingTambahan ON s.IDSidang = dsPembimbingTambahan.idSidang "
                + "AND dsPembimbingTambahan.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 2') "
                + "LEFT JOIN Dosen dPembimbingTambahan ON dsPembimbingTambahan.NIP = dPembimbingTambahan.NIP "
                + "LEFT JOIN DosenSidang dsKetuaPenguji ON s.IDSidang = dsKetuaPenguji.idSidang "
                + "AND dsKetuaPenguji.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 1') "
                + "LEFT JOIN Dosen dKetuaPenguji ON dsKetuaPenguji.NIP = dKetuaPenguji.NIP "
                + "LEFT JOIN DosenSidang dsAnggotaPenguji ON s.IDSidang = dsAnggotaPenguji.idSidang "
                + "AND dsAnggotaPenguji.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 2') "
                + "LEFT JOIN Dosen dAnggotaPenguji ON dsAnggotaPenguji.NIP = dAnggotaPenguji.NIP "
                + "WHERE s.IDSidang = ?";

        List<isiBAP> results = jdbc.query(query, this::mapRowToIsiBAP, idSidang);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public nilai findNilaiBAP(int idSidang) {
        String query = """
                                WITH RecentKomponen AS (
                    SELECT
                        IDKomp,
                        komponen,
                        bobotPenguji,
                        bobotPembimbing
                    FROM
                        KomponenNilai
                    WHERE
                        tglBerlaku = (SELECT MAX(tglBerlaku) FROM KomponenNilai)
                ),
                DosenNilai AS (
                    SELECT
                        ds.idSidang,
                        st.jenisTA,
                        rd.namaRole,
                        rk.bobotPenguji,
                        rk.bobotPembimbing,
                        CASE
                            WHEN rd.namaRole = 'Penguji 1' THEN ns.nilaiPenguji1
                            WHEN rd.namaRole = 'Penguji 2' THEN ns.nilaiPenguji2
                            WHEN rd.namaRole = 'Pembimbing 1' THEN ns.nilaiPembimbing
                            ELSE NULL
                        END AS nilaiPerKomponen
                    FROM
                        DosenSidang ds
                    INNER JOIN
                        RoleDosen rd ON ds.idRole = rd.idRole
                    INNER JOIN
                        RecentKomponen rk ON TRUE
                    LEFT JOIN
                        NilaiSidang ns ON ds.idSidang = ns.IDSidang AND rk.IDKomp = ns.IDKomp
                    INNER JOIN
                        SidangTA st ON ds.idSidang = st.IDSidang
                ),
                FinalResult AS (
                    SELECT
                        idSidang,
                        jenisTA,
                        namaRole,
                        ROUND(SUM(
                            CASE
                                WHEN namaRole IN ('Penguji 1', 'Penguji 2') THEN nilaiPerKomponen * (bobotPenguji / 100)
                                WHEN namaRole = 'Pembimbing 1' THEN nilaiPerKomponen * (bobotPembimbing / 100)
                                ELSE 0
                            END
                        ), 2) AS nilaiAkhir
                    FROM
                        DosenNilai
                    WHERE
                        namaRole != 'Pembimbing 2'
                    GROUP BY
                        idSidang, jenisTA, namaRole
                ),
                KoordinatorNilai AS (
                    SELECT
                        idSidang,
                        jenisTA,
                        'Koordinator' AS namaRole,
                        ROUND(nilaiKoordinator, 2) AS nilaiAkhir
                    FROM
                        SidangTA
                ),
                BobotDosen AS (
                    SELECT
                        bd.idRole,           -- Tambahkan alias tabel 'bd' di sini
                        rd.namaRole,
                        bd.jenisTA,
                        bd.bobot
                    FROM
                        bobotDosen bd        -- Alias 'bd' untuk bobotDosen
                    INNER JOIN
                        RoleDosen rd ON bd.idRole = rd.idRole
                ),
                CombinedResult AS (
                    SELECT
                        fr.idSidang,
                        fr.jenisTA,
                        fr.namaRole,
                        fr.nilaiAkhir,
                        bd.bobot
                    FROM
                        FinalResult fr
                    LEFT JOIN
                        BobotDosen bd ON fr.namaRole = bd.namaRole AND fr.jenisTA = bd.jenisTA
                    UNION ALL
                    SELECT
                        kn.idSidang,
                        kn.jenisTA,
                        kn.namaRole,
                        kn.nilaiAkhir,
                        bd.bobot
                    FROM
                        KoordinatorNilai kn
                    LEFT JOIN
                        BobotDosen bd ON kn.namaRole = bd.namaRole AND kn.jenisTA = bd.jenisTA
                ),
                PivotedResult AS (
                    SELECT
                        idSidang,
                        jenisTA,
                        MAX(CASE WHEN namaRole = 'Penguji 1' THEN 'Ketua Penguji' END) AS namaRoleKetua,
                        MAX(CASE WHEN namaRole = 'Penguji 1' THEN nilaiAkhir END) AS nilaiKetua,
                        MAX(CASE WHEN namaRole = 'Penguji 1' THEN bobot END) AS bobotKetua,

                        MAX(CASE WHEN namaRole = 'Penguji 2' THEN 'Anggota Penguji' END) AS namaRoleAnggota,
                        MAX(CASE WHEN namaRole = 'Penguji 2' THEN nilaiAkhir END) AS nilaiAnggota,
                        MAX(CASE WHEN namaRole = 'Penguji 2' THEN bobot END) AS bobotAnggota,

                        MAX(CASE WHEN namaRole = 'Pembimbing 1' THEN 'Pembimbing' END) AS namaRolePembimbing,
                        MAX(CASE WHEN namaRole = 'Pembimbing 1' THEN nilaiAkhir END) AS nilaiPembimbing,
                        MAX(CASE WHEN namaRole = 'Pembimbing 1' THEN bobot END) AS bobotPembimbing,

                        MAX(CASE WHEN namaRole = 'Koordinator' THEN 'Koordinator' END) AS namaRoleKoordinator,
                        MAX(CASE WHEN namaRole = 'Koordinator' THEN nilaiAkhir END) AS nilaiKoordinator,
                        MAX(CASE WHEN namaRole = 'Koordinator' THEN bobot END) AS bobotKoordinator
                    FROM
                        CombinedResult
                    GROUP BY
                        idSidang, jenisTA
                )
                SELECT
                    idSidang,
                    jenisTA,

                    namaRoleKetua,
                    COALESCE(nilaiKetua, 0) AS nilaiKetua,
                    COALESCE(bobotKetua, 0) AS bobotKetua,
                    COALESCE(ROUND(nilaiKetua * (bobotKetua / 100), 2), 0) AS nilaiAkhirKetua,

                    namaRoleAnggota,
                    COALESCE(nilaiAnggota, 0) AS nilaiAnggota,
                    COALESCE(bobotAnggota, 0) AS bobotAnggota,
                    COALESCE(ROUND(nilaiAnggota * (bobotAnggota / 100), 2), 0) AS nilaiAkhirAnggota,

                    namaRolePembimbing,
                    COALESCE(nilaiPembimbing, 0) AS nilaiPembimbing,
                    COALESCE(bobotPembimbing, 0) AS bobotPembimbing,
                    COALESCE(ROUND(nilaiPembimbing * (bobotPembimbing / 100), 2), 0) AS nilaiAkhirPembimbing,

                    namaRoleKoordinator,
                    COALESCE(nilaiKoordinator, 0) AS nilaiKoordinator,
                    COALESCE(bobotKoordinator, 0) AS bobotKoordinator,
                    COALESCE(ROUND(nilaiKoordinator * (bobotKoordinator / 100), 2), 0) AS nilaiAkhirKoordinator,

                    ROUND(
                        COALESCE(nilaiKetua * (bobotKetua / 100), 0) +
                        COALESCE(nilaiAnggota * (bobotAnggota / 100), 0) +
                        COALESCE(nilaiPembimbing * (bobotPembimbing / 100), 0) +
                        COALESCE(nilaiKoordinator * (bobotKoordinator / 100), 0),
                    2) AS totalNilaiAkhir
                FROM
                    PivotedResult
                WHERE
                	idsidang=?;

                                """;

        List<nilai> results = jdbc.query(query, this::mapRowToNilai, idSidang);
        return results.isEmpty() ? null : results.get(0);
    }

    private isiBAP mapRowToIsiBAP(ResultSet rs, int rowNum) throws SQLException {
        return new isiBAP(
                rs.getString("semester"),
                rs.getString("tahunajaran"),
                rs.getInt("jenista"),
                rs.getString("npm"),
                rs.getString("nama"),
                rs.getString("judulta"),
                rs.getString("dosbim1"),
                rs.getString("dosbim2"),
                rs.getString("dosji1"),
                rs.getString("dosji2"),
                rs.getDate("tanggalsidang"));
    }

    private nilai mapRowToNilai(ResultSet rs, int rowNum) throws SQLException {
        return new nilai(

                rs.getDouble("nilaiketua"), // Mapping nilaiKetua
                rs.getDouble("bobotketua"), // Mapping bobotKetua
                rs.getDouble("nilaiakhirketua"), // Mapping nilaiAkhirKetua (calculated)
                rs.getDouble("nilaianggota"), // Mapping nilaiAnggota
                rs.getDouble("bobotanggota"), // Mapping bobotAnggota
                rs.getDouble("nilaiakhiranggota"), // Mapping nilaiAkhirAnggota (calculated)
                rs.getDouble("nilaipembimbing"), // Mapping nilaiPembimbing
                rs.getDouble("bobotpembimbing"), // Mapping bobotPembimbing
                rs.getDouble("nilaiakhirpembimbing"), // Mapping nilaiAkhirPembimbing (calculated)
                rs.getDouble("nilaikoordinator"), // Mapping nilaiKoordinator
                rs.getDouble("bobotkoordinator"), // Mapping bobotKoordinator
                rs.getDouble("nilaiakhirkoordinator"), // Mapping nilaiAkhirKoordinator (calculated)
                rs.getDouble("totalnilaiakhir") // Mapping totalNilaiAkhir (calculated)
        );
    }
}

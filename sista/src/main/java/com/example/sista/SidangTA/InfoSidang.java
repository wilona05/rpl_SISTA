package com.example.sista.SidangTA;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class InfoSidang {
    private String nama;
    private String npm;
    int idsidang;
    private String judulTA;
    private int jenisTA;
    private Timestamp jadwal;
    private String tempat;
    private String semester;
    private String tahunAjaran;
    private String catatanRevisi;
    private String pembimbingUtama;
    private String pembimbingTambahan;
    private String ketuaPenguji;
    private String anggotaPenguji;
}

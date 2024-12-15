package com.example.sista.SidangTA;

import lombok.Data;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SidangTA {
    public Integer id;
    private String nama;
    private String npm;
    private String email;
    private String judulTA;
    private int jenisTA;
    private Timestamp jadwal;
    private String tempat;
    private String semester;
    private String tahunAjaran;
    private String catatanRevisi;
    private int nilaiKoordinator;
    private String dosenPembimbing1;
    private String dosenPembimbing2;
    private String dosenPenguji1; // ketua penguji
    private String dosenPenguji2; // anggota penguji

    public SidangTA(Integer id, String nama, String npm, String email, int jenisTA, String judulTA, Timestamp jadwal,
            String tempat, String semester, String tahunAjaran, String catatanRevisi, int nilaiKoordinator,
            String dosenPembimbing1, String dosenPembimbing2, String dosenPenguji1,
            String dosenPenguji2) {
        this.id = id;
        this.nama = nama;
        this.npm = npm;
        this.email = email;
        this.judulTA = judulTA;
        this.jenisTA = jenisTA;
        this.jadwal = jadwal;
        this.tempat = tempat;
        this.semester = semester;
        this.tahunAjaran = tahunAjaran;
        this.catatanRevisi = catatanRevisi;
        this.nilaiKoordinator = nilaiKoordinator;
        this.dosenPembimbing1 = dosenPembimbing1;
        this.dosenPembimbing2 = dosenPembimbing2;
        this.dosenPenguji1 = dosenPenguji1;
        this.dosenPenguji2 = dosenPenguji2;
    }

    public SidangTA(String nama, Timestamp jadwal) {
        this.nama = nama;
        this.jadwal = jadwal;
    }
}
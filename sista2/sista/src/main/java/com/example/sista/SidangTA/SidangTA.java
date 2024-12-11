package com.example.sista.SidangTA;

import lombok.Data;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SidangTA {
    private String nama;
    private String npm;
    private String email;
    private String judulTA;
    private int jenisTA;
    private Timestamp jadwal;
    private String tempat;
    private String dosenPembimbing;
    private String dosenPenguji1;
    private String dosenPenguji2;

    public SidangTA(String nama, String npm, String email, int jenisTA, String judulTA, Timestamp jadwal, String tempat,
            String dosenPembimbing, String dosenPenguji1, String dosenPenguji2) {
        this.nama = nama;
        this.npm = npm;
        this.email = email;
        this.judulTA = judulTA;
        this.jenisTA = jenisTA;
        this.jadwal = jadwal;
        this.tempat = tempat;
        this.dosenPembimbing = dosenPembimbing;
        this.dosenPenguji1 = dosenPenguji1;
        this.dosenPenguji2 = dosenPenguji2;
    }

    public SidangTA(String npm, Timestamp jadwal) {
        this.npm = npm;
        this.jadwal = jadwal;
    }
}
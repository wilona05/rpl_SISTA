package com.example.sista.InputNilai;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Nilai {
    private final Integer idSidang;
    private final Integer idKomp;
    private String namaRole;
    private final String komponen;
    private final BigDecimal bobot;
    private final BigDecimal nilai;
    private BigDecimal nilaiAkhir;

    public Nilai(Integer idSidang, Integer idKomp, String namaRole, String komponen, BigDecimal bobot, BigDecimal nilai,
            BigDecimal nilaiAkhir) {
        this.idSidang = idSidang;
        this.idKomp = idKomp;
        this.namaRole = namaRole;
        this.komponen = komponen;
        this.bobot = bobot;
        this.nilai = nilai;
        this.nilaiAkhir = nilaiAkhir;
    }
}

/*
 * ambil id sidang
 * select dari nilaidosen
 * kalau udah dapet semua, kaliin bobot sama nilai
 * kalau di sql, select > groupby namarole > untuk setiap nama role
 * sum(bobot*komponen)
 */
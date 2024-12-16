package com.example.sista.InputNilai;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class NilaiAkhir {
    private final Integer idSidang;
    private String namaRole;
    private final BigDecimal bobot;
    private final BigDecimal nilai;
    private BigDecimal nilaiAkhir;

    public NilaiAkhir(Integer idSidang, String namaRole, BigDecimal nilai, BigDecimal bobot, BigDecimal nilaiAkhir) {
        this.idSidang = idSidang;
        this.namaRole = namaRole;
        this.nilai = nilai;
        this.bobot = bobot;
        this.nilaiAkhir = nilaiAkhir;
    }
}

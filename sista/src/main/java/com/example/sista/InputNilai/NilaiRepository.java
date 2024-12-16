package com.example.sista.InputNilai;

import java.math.BigDecimal;
import java.util.List;

public interface NilaiRepository {
    String getNamaRole(Integer idSidang, String nip);

    List<Nilai> getNilai(Integer idSidang, String namaRole);

    boolean inputNilai(Integer idSidang, Integer idKomp, BigDecimal nilai, String namaRole);

    List<Nilai> getAllNilai(Integer idSidang);

    List<NilaiAkhir> hasilNilaiAkhir(Integer idSidang);

    BigDecimal totalNilai(Integer idSidang);
}
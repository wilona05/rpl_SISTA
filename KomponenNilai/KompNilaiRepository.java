package com.example.sista.KomponenNilai;

import java.math.BigDecimal;
import java.util.List;
import java.sql.Date;

public interface KompNilaiRepository {
    List<KomponenNilai> findAll();
    void addKomponenNilai(String komponen, BigDecimal bobotPenguji, BigDecimal bobotPembimbing, Date tglBerlaku);
    boolean deleteKomponenNilai(Integer idKomp);
    void updateKomponenNilai(Integer idKomp, String komponen, BigDecimal bobotPenguji, BigDecimal bobotPembimbing, Date tglBerlaku);
}

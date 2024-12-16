package com.example.sista.KomponenNilai;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

@Data
public class KomponenNilai {
    private final Integer idKomp;
    private final String komponen;
    private final BigDecimal bobotPenguji;
    private final BigDecimal bobotPembimbing;
    private final Date tglBerlaku;
}
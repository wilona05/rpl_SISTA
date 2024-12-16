package com.example.sista.InputNilai;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Nilai {
    private final Integer idSidang;
    private final Integer idKomp;
    private final String komponen;
    private final BigDecimal bobot;
    private final BigDecimal nilai;
}

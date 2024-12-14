package com.example.sista.BobotDosen;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BobotDosen {
    private final Integer jenisTA;
    private final Integer idBobot;
    private final BigDecimal bobot;
    private final Integer idRole;
    private final String namaRole;
}

package com.example.sista.Sidang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Sidang {
    int idsidang;
    int jenita;
    String judulta;
    Date jadwal;
    String tempat;
    String semester;
    String tahunajaran;
    String catatanrevisi;
    String npm;
    int nilaikoordinator;
}
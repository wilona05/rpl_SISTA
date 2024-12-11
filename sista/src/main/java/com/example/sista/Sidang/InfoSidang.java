package com.example.sista.Sidang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class InfoSidang {
    String namamahasiswa;
    String email;
    int idsidang;
    int jenista;
    String judulta;
    Date jadwal;
    String tempat;
    String semester;
    String tahunajaran;
    String catatanrevisi;
    String npm;
    String pembimbingutama;
    String pembimbingtambahan;
    String ketuapenguji;
    String anggotapenguji;
}

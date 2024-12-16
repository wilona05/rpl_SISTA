package com.example.sista.BAP;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class isiBAP {
    private String semester;
    private String tahunajaran;
    private int jenista;
    private String npm;
    private String nama;
    private String judulta;
    private String dosbim1;
    private String dosbim2;
    private String dosji1;
    private String dosji2;
    private Date tanggalsidang;
}

package com.example.sista.SidangTA;

import java.util.List;

public interface SidangRepository {
    List<SidangTA> findAll();

    SidangTA getSidangByNama(String nama);

    boolean registerSidang(SidangTA sidang);
}
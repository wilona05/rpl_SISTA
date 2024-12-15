package com.example.sista.SidangTA;

import java.util.List;

public interface SidangTARepository {
    List<SidangTA> findAll();

    SidangTA getSidangByNama(String nama);

    boolean registerSidang(SidangTA sidang);

    List<SidangTA> findByRole(int idrole, String nip);

    List<SidangTA> getAllSidang();

    List<SidangTA> getSidangItemsByRole(int idrole, String nip);

    List<InfoSidang> getInfoSidangById(int id);

    List<SidangTA> getSidangByDosen(String nip);
}
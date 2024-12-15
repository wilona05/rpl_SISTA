package com.example.sista.SidangTA;

import java.util.List;

public interface SidangTARepository {
    List<SidangTA> findAll();

    boolean registerSidang(SidangTA sidang);

    List<SidangTA> findByRole(int idrole, String nip);

    List<SidangTA> getAllSidang();

    SidangTA getSidangByNama(String nama);

    List<SidangTA> getSidangItemsByRole(int idrole, String nip);

    List<SidangTA> getSidangByDosen(String nip);

    List<SidangTA> getFilteredSidang(String nip, String keyword, String keyword2);

    List<SidangTA> getInfoSidangById(int id);

    List<SidangTA> getSidangByMahasiswa(String npm);
}
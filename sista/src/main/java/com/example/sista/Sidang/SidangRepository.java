package com.example.sista.Sidang;

import java.util.List;

public interface SidangRepository {
    List<Sidang> findByRole(int idrole, String nip);
    List<Sidang> getAllSidang();
    List<Sidang> getSidangItemsByRole(int idrole, String nip);
}

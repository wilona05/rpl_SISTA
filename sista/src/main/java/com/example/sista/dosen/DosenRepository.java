package com.example.sista.Dosen;

public interface DosenRepository {
    public Boolean checkStatusKoord(String email);

    String getNipDosenbyEmail(String email);

    String getNipDosenbyNama(String nama);
}
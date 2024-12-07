package com.example.sista.dosen;

import jakarta.servlet.http.HttpSession;

public interface DosenRepository {
    public Boolean checkStatusKoord(String email);
}

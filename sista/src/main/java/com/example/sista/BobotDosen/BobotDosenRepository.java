package com.example.sista.BobotDosen;

import java.math.BigDecimal;
import java.util.List;

public interface BobotDosenRepository {
    List<BobotDosen> findAll();
    void updateBobot(Integer idBobot, BigDecimal bobot);
}

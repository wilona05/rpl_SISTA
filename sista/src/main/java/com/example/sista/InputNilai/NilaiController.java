package com.example.sista.InputNilai;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class NilaiController {

    @Autowired
    NilaiRepository repo;

    @GetMapping("/kelolaNilai")
    public String kelolaNilai(HttpSession httpSession, Model model) {
        Integer idSidang = (Integer) httpSession.getAttribute("idSidang");
        String nip = ((String) httpSession.getAttribute("email")).replace("@dosen.edu", "");
        String namaRole = this.repo.getNamaRole(idSidang, nip);
        List<Nilai> nilai = this.repo.getNilai(idSidang, namaRole);

        model.addAttribute("nilai", nilai);
        model.addAttribute("edit", false);
        return "dosen/inputNilai";
    }

    @GetMapping("/editNilai")
    public String editNilai(HttpSession httpSession, Model model) {
        Integer idSidang = (Integer) httpSession.getAttribute("idSidang");
        String nip = ((String) httpSession.getAttribute("email")).replace("@dosen.edu", "");
        String namaRole = this.repo.getNamaRole(idSidang, nip);
        List<Nilai> nilai = this.repo.getNilai(idSidang, namaRole);

        model.addAttribute("nilai", nilai);
        model.addAttribute("edit", true);
        return "dosen/inputNilai";
    }

    @PostMapping("/saveNilai")
    public String saveBobotDosen(@RequestParam Map<String, String> nilaiData, HttpSession httpSession) {
        Integer idSidang = (Integer) httpSession.getAttribute("idSidang");
        String nip = ((String) httpSession.getAttribute("email")).replace("@dosen.edu", "");
        String namaRole = this.repo.getNamaRole(idSidang, nip);

        for (String key : nilaiData.keySet()) {
            String idKey = key.replace("nilai", "");
            Integer idKomp = Integer.valueOf(idKey);
            BigDecimal newNilai = new BigDecimal(nilaiData.get(key));
            this.repo.inputNilai(idSidang, idKomp, newNilai, namaRole);
        }
        return "redirect:/sista/dashboardDosen/kelolaNilai";
    }
}

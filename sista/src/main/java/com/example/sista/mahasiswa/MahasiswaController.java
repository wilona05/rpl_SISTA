package com.example.sista.Mahasiswa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sista.SidangTA.SidangTA;
import com.example.sista.SidangTA.SidangTARepository;
import com.example.sista.BAP.nilai;
import com.example.sista.Dosen.DosenRepository;
import com.example.sista.InputNilai.Nilai;
import com.example.sista.InputNilai.NilaiAkhir;
import com.example.sista.InputNilai.NilaiRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sista/dashboardMahasiswa")
public class MahasiswaController {

    @Autowired
    SidangTARepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @Autowired
    MahasiswaRepository repoMahasiswa;

    @Autowired
    NilaiRepository repoNilai;

    @GetMapping()
    public String dashboard(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        String npm = repoMahasiswa.getNpmMahasiswa(email);
        List<SidangTA> listSidang = repoSidang.getSidangByMahasiswa(npm);
        model.addAttribute("showContainer", false);
        model.addAttribute("listSidang", listSidang);
        return "mahasiswa/dashboardMahasiswa";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model) {
        List<SidangTA> listSidang = repoSidang.getInfoSidangById(id);

        if (listSidang == null) {
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page";
        }

        SidangTA sidangTA = listSidang.get(0);
        model.addAttribute("sidangTA", sidangTA);
        return "mahasiswa/infoSidangMahasiswa";
    }

    // @GetMapping("/catatanRevisi")
    // public String lihatCatatanRevisi(){

    // }

    // @GetMapping("/lihatBAP")
    // public String lihatBAP(){

    // }

    @GetMapping("/lihatNilai")
    public String lihatNilai(@RequestParam("id") int id, Model model) {
        List<NilaiAkhir> nilaiAkhir = repoNilai.hasilNilaiAkhir(id);

        if (nilaiAkhir == null) {
            model.addAttribute("errorMessage", "Nilai not found");
            return "error-page";
        }

        BigDecimal totalNilai = repoNilai.totalNilai(id);

        model.addAttribute("nilaiAkhir", nilaiAkhir);
        model.addAttribute("totalNilai", totalNilai);
        return "mahasiswa/nilaiSidangMhs";
    }
}
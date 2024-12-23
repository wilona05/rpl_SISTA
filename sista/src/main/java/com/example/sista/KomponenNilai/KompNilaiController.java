package com.example.sista.KomponenNilai;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sista/dashboardKoordinator")
public class KompNilaiController {

    @Autowired
    private KompNilaiRepository repo;

    @GetMapping("/komponenNilai")
    public String komponenNilai(Model model) {
        List<KomponenNilai> komponenNilai = this.repo.findAll();
        model.addAttribute("komponenNilai", komponenNilai);
        model.addAttribute("edit", false);
        return "dosen/komponenNilai";
    }

    @GetMapping("/editKomponenNilai")
    public String editKomponenNilai(Model model){
        List<KomponenNilai> komponenNilai = this.repo.findAll();
        model.addAttribute("komponenNilai", komponenNilai);
        model.addAttribute("edit", true);
        return "dosen/komponenNilai";
    }

    @PostMapping("/addKomponenNilai")
    public String addKomponenNilai(@RequestParam String komponen, 
        @RequestParam BigDecimal bobotPenguji, @RequestParam BigDecimal bobotPembimbing){
            this.repo.addKomponenNilai(komponen, bobotPenguji, bobotPembimbing);
            return "redirect:/sista/dashboardKoordinator/komponenNilai";
    }

    @PostMapping("/deleteKomponenNilai")
    public String deleteKomponenNilai(@RequestParam Integer idKomp, Model model){
        boolean success = this.repo.deleteKomponenNilai(idKomp);
        if(success){
            return "redirect:/sista/dashboardKoordinator/komponenNilai";
        }

        List<KomponenNilai> komponenNilai = this.repo.findAll();
        model.addAttribute("komponenNilai", komponenNilai);
        model.addAttribute("edit", true);
        model.addAttribute("error", "Gagal delete komponen");
        return "dosen/komponenNilai";
    }

    @PostMapping("/saveKomponenNilai")
    public String saveKomponenNilai(@RequestParam Map<String, String> kompNilaiData,
        @RequestParam Map<String, String> bobotPengujiData,
        @RequestParam Map<String, String> bobotPembimbingData){
            kompNilaiData.forEach((key, komponenValue) -> {
                if(key.startsWith("kompNilai")){
                    String idKey = key.replace("kompNilai", "");
                    Integer id = Integer.valueOf(idKey);
                    String komponen = kompNilaiData.get(key);

                    String bobotPengujiKey = "bobotPenguji" + id;
                    BigDecimal bobotPenguji = new BigDecimal(bobotPengujiData.get(bobotPengujiKey));

                    String bobotPembimbingKey = "bobotPembimbing" + id;
                    BigDecimal bobotPembimbing = new BigDecimal(bobotPembimbingData.get(bobotPembimbingKey));

                    this.repo.updateKomponenNilai(id, komponen, bobotPenguji, bobotPembimbing);
                }

            });
            return "redirect:/sista/dashboardKoordinator/komponenNilai";
    }
}

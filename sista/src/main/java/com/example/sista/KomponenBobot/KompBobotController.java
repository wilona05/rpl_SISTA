package com.example.sista.KomponenBobot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/sista/dashboardKoordinator")
public class KompBobotController {

    @Autowired
    private KompBobotRepository repo;

    @GetMapping("/komponenNilai")
    public String komponenNilai(Model model) {
        List<KomponenNilai> komponenNilai = this.repo.findAll();
        model.addAttribute("komponenNilai", komponenNilai);
        return "dosen/komponenNilai";
    }
}

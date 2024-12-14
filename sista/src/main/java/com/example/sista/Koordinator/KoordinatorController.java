package com.example.sista.Koordinator;

import java.util.List;

import com.example.sista.dosen.DosenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sista.Sidang.Sidang;
import com.example.sista.Sidang.SidangRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sista/dashboardKoordinator")
public class KoordinatorController {
    
    @Autowired
    SidangRepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @GetMapping
    public String dashboard(Model model, HttpSession httpSession){
        String email = (String) httpSession.getAttribute("email");
        List<Sidang> listSidang = repoSidang.getSidangByDosen(repoDosen.getNipDosen(email));
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }
}

package com.example.sista.Dosen;

import com.example.sista.SidangTA.SidangTARepository;
import com.example.sista.SidangTA.InfoSidang;
import com.example.sista.SidangTA.SidangTA;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class DosenController {

    @Autowired
    SidangTARepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @GetMapping()
    public String dashboard(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        // List<SidangTA> listSidang =
        // repoSidang.getSidangByDosen(repoDosen.getNipDosen(email));
        // model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name = "pickRole", required = false) Integer role,
            Model model,
            HttpSession httpSession) {
        if (role == null) {
            model.addAttribute("errorMessage", "Please select a role first.");
            model.addAttribute("showContainer", false);
            return "dosen/dashboardDosen";
        }

        // String email = (String) httpSession.getAttribute("email");
        // List<SidangTA> listSidang = repoSidang.getSidangItemsByRole(role, email);
        // model.addAttribute("showContainer", true); // Indicate to show the container
        // model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model) {

        List<InfoSidang> listSidang = repoSidang.getInfoSidangById(id);

        if (listSidang == null) {
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page";
        }

        InfoSidang sidang = listSidang.get(0);
        model.addAttribute("sidang", sidang);
        return "dosen/infoSidang";
    }
}
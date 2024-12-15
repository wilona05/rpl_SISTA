package com.example.sista.Koordinator;

import java.util.List;

import com.example.sista.dosen.DosenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.sista.Sidang.Sidang;
import com.example.sista.Sidang.SidangRepository;
import com.example.sista.Sidang.InfoSidang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        model.addAttribute("showContainer", false);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name = "pickRole", required = false) Integer role,
                              Model model,
                              HttpSession httpSession) {

        if (role == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Please select a role first.");
            model.addAttribute("showContainer", false);
            return "dosen/dashboardKoordinator"; // Reload dashboard with error
        }

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosen(email);
        List<Sidang> listSidang = repoSidang.getSidangItemsByRole(role, nip);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model) {
        // Simulated service call to fetch sidang details by ID
        List<InfoSidang> listSidang = repoSidang.getInfoSidangById(id);

        if (listSidang == null) {
            // Handle case where the sidang is not found
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page"; // Replace with the appropriate error page
        }

        InfoSidang sidang = listSidang.get(0);
        // Add the sidang details to the model for rendering in the Thymeleaf template
        model.addAttribute("sidang", sidang);
        return "dosen/infoSidang"; // Return the Thymeleaf template name
    }

    @GetMapping("/filteredSidang")
    public String getFilteredSidang(@RequestParam(value = "filter", required = false) String keyword, Model model, HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosen(email);
        List<Sidang> listSidang = repoSidang.getFilteredSidang(nip, keyword, keyword);

        Logger logger = LoggerFactory.getLogger(KoordinatorController.class);
        logger.info("Sidang: {}", listSidang);
        logger.info("keyword: {}", keyword);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

}


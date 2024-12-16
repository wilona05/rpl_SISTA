package com.example.sista.Dosen;

import com.example.sista.SidangTA.SidangTARepository;
import com.example.sista.SidangTA.InfoSidang;
import com.example.sista.SidangTA.SidangTA;

import jakarta.servlet.http.HttpSession;
import java.util.stream.Collectors;

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
        List<SidangTA> listSidang = repoSidang.getSidangByDosen(repoDosen.getNipDosenbyEmail(email));
        model.addAttribute("showContainer", false);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name = "pickRole", required = false) Integer role,
            Model model,
            HttpSession httpSession) {

        if (role == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Please select a role first.");
            model.addAttribute("showContainer", false);
            return "dosen/dashboardDosen";
        }

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosenbyEmail(email);
        List<SidangTA> listSidang = repoSidang.getSidangItemsByRole(role, nip);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model) {

        List<SidangTA> listSidang = repoSidang.getInfoSidangById(id);

        if (listSidang == null) {
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page";
        }

        SidangTA sidang = listSidang.get(0);
        model.addAttribute("sidang", sidang);
        return "dosen/infoSidang";
    }

    @GetMapping("/filteredSidang")
    public String getFilteredSidang(@RequestParam(value = "filter", required = false) String keyword, Model model,
            HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosenbyEmail(email);
        List<SidangTA> listSidang = repoSidang.getFilteredSidang(nip, keyword, keyword);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }
}

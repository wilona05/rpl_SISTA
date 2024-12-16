package com.example.sista.dosen;

import com.example.sista.Sidang.InfoSidang;
import com.example.sista.Sidang.Sidang;
import com.example.sista.Sidang.SidangRepository;
import com.example.sista.Users.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class DosenController {

    @Autowired
    SidangRepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @Autowired
    UserRepository repoUser;

    @GetMapping()
    public String dashboard(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        List<Sidang> listSidang = repoSidang.getSidangByDosen(repoDosen.getNipDosen(email));
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
            return "dosen/dashboardDosen"; // Reload dashboard with error
        }

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosen(email);
        List<Sidang> listSidang = repoSidang.getSidangItemsByRole(role, nip);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model, HttpSession session) {
        // Simulated service call to fetch sidang details by ID
        List<InfoSidang> listSidang = repoSidang.getInfoSidangById(id);

        String email = (String) session.getAttribute("email");

        if (listSidang == null) {
            // Handle case where the sidang is not found
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page"; // Replace with the appropriate error page
        }
        System.out.println(email);

        boolean pembimbing = repoUser.getStatusPembimbing(id, email);

        System.out.println(pembimbing);

        model.addAttribute("pembimbing", pembimbing);

        session.setAttribute("idSidang", id);

        InfoSidang sidang = listSidang.get(0);
        // Add the sidang details to the model for rendering in the Thymeleaf template
        model.addAttribute("sidang", sidang);
        model.addAttribute("idSidang", id);
        return "dosen/infoSidang"; // Return the Thymeleaf template name
    }

    @GetMapping("/filteredSidang")
    public String getFilteredSidang(@RequestParam(value = "filter", required = false) String keyword, Model model,
            HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosen(email);
        List<Sidang> listSidang = repoSidang.getFilteredSidang(nip, keyword, keyword);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardDosen";
    }

    @GetMapping("/revisi")
    public String showHalamanRevisi(HttpSession session, Model model) {

        int id = (int) session.getAttribute("idSidang");

        repoSidang.getInfoSidangById(id);

        List<InfoSidang> listSidang = repoSidang.getInfoSidangById(id);

        model.addAttribute("isiRevisi", listSidang.get(0).getCatatanrevisi());

        session.setAttribute("idSidang", id);

        return "dosen/catatanRevisi";
    }

    @PostMapping("/revisi/submit")
    public String submitCatatanRevisi(@RequestParam("catatanRevisi") String revisi,
            HttpSession session) {

        repoDosen.inputRevisi((int) session.getAttribute("idSidang"), revisi);

        return "redirect:/sista/dashboardDosen/revisi";
    }
}

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

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name = "pickRole", required = false) Integer role,
                              Model model,
                              HttpSession httpSession) {
        // Validate if role is selected
        // role = 1;
        if (role == null) {
            model.addAttribute("errorMessage", "Please select a role first.");
            model.addAttribute("showContainer", false);
            return "dosen/dashboard"; // Reload dashboard with error
        }

        // Fetch email from session
        String email = (String) httpSession.getAttribute("email");

        // Retrieve Sidang items for the selected role
        List<Sidang> listSidang = repoSidang.getSidangItemsByRole(role, email);

        // Debug dengan hardcoded
        // List<Sidang> listSidang = new ArrayList<>();
        // Date date = new Date(2024, 12, 21, 12 , 23);
        // Sidang sidang = new Sidang(1, 1, "asdas", date, "temapt", "2", "2024", "note", "1234", 100);
        // listSidang.add(sidang);

        // Log the role and email values
//        Logger logger = LoggerFactory.getLogger(DosenController.class);
        // logger.info("Role: {}", role);
        // logger.info("Email: {}", email);
//        logger.info("Email: {}", listSidang);

        // Add attributes to the model
        model.addAttribute("showContainer", true); // Indicate to show the container
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardkoordinator";
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

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {

        httpSession.invalidate();

        return "redirect:/sista/login";
    }
}


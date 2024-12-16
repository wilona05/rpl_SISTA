package com.example.sista.Koordinator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sista.Dosen.DosenRepository;
import com.example.sista.SidangTA.SidangTARepository;
import com.example.sista.SidangTA.InfoSidang;
import com.example.sista.SidangTA.SidangTA;
import com.example.sista.Users.User;
import com.example.sista.Users.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sista/dashboardKoordinator")
public class KoordinatorController {

    @Autowired
    SidangTARepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @Autowired
    private UserService userService;

    @GetMapping
    public String dashboard(Model model, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        List<SidangTA> listSidang = repoSidang.getSidangByDosen(repoDosen.getNipDosenbyEmail(email));
        model.addAttribute("showContainer", false);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

    @GetMapping("/daftarSidang")
    public String daftarSidang(Model model) {
        List<User> dosenList = userService.getAllDosen();
        model.addAttribute("dosenList", dosenList);
        return "dosen/registrasiSidang";
    }

    @PostMapping("/daftarSidang")
    public String registerSidang(@RequestParam("nama") String nama,
            @RequestParam("npm") String npm, @RequestParam("judulTA") String judulTA,
            @RequestParam("jenisTA") int jenisTA, @RequestParam("jadwal") String jadwalString,
            @RequestParam("tempat") String tempat,
            @RequestParam("dosenPembimbing1") String dosenPembimbing1,
            @RequestParam("dosenPembimbing2") String dosenPembimbing2,
            @RequestParam("dosenPenguji1") String dosenPenguji1, @RequestParam("dosenPenguji2") String dosenPenguji2,
            Model model) {
        LocalDateTime jadwalDateTime = LocalDateTime.parse(jadwalString);
        Timestamp jadwal = Timestamp.valueOf(jadwalDateTime);
        String semester = "";
        if (jadwalDateTime.getMonthValue() >= 8 && jadwalDateTime.getMonthValue() <= 11) {
            semester = "Ganjil";
        } else {
            semester = "Genap";
        }
        String tahunAjaran = jadwalDateTime.getYear() + "/" + (jadwalDateTime.getYear() + 1);
        String catatanRevisi = "";
        int nilaiKoordinator = 100;
        int id = 0;

        SidangTA sidangTA = new SidangTA(id, nama, npm, judulTA, jenisTA, jadwal, tempat, semester,
                tahunAjaran, catatanRevisi, nilaiKoordinator, dosenPembimbing1, dosenPembimbing2, dosenPenguji1,
                dosenPenguji2);
        boolean success = this.repoSidang.registerSidang(sidangTA);

        if (success) {
            model.addAttribute("sidangTA", sidangTA);
            model.addAttribute("editSidang", false);
            return "dosen/infoSidang";
        } else {
            model.addAttribute("error", "Gagal menyimpan sidang");
            return "dosen/registrasiSidang";
        }
    }

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name = "pickRole", required = false) Integer role, Model model,
            HttpSession httpSession) {
        if (role == null) {
            model.addAttribute("errorMessage", "Please select a role first.");
            model.addAttribute("showContainer", false);
            return "dosen/dashboardKoordinator";
        }

        String nip = ((String) httpSession.getAttribute("email")).replace("@dosen.edu", " ").trim();
        List<SidangTA> listSidang = repoSidang.getSidangItemsByRole(role, nip);
        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

    @GetMapping("/infoSidang")
    public String getInfoSidang(@RequestParam("id") int id, Model model) {
        // Simulated service call to fetch sidang details by ID
        List<SidangTA> listSidang = repoSidang.getInfoSidangById(id);

        if (listSidang == null) {
            // Handle case where the sidang is not found
            model.addAttribute("errorMessage", "Sidang not found");
            return "error-page"; // Replace with the appropriate error page
        }

        SidangTA sidang = listSidang.get(0);
        // Add the sidang details to the model for rendering in the Thymeleaf template
        model.addAttribute("sidang", sidang);
        return "dosen/infoSidang"; // Return the Thymeleaf template name
    }

    @GetMapping("/filteredSidang")
    public String getFilteredSidang(@RequestParam(value = "filter", required = false) String keyword, Model model,
            HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("email");
        String nip = repoDosen.getNipDosenbyEmail(email);
        List<SidangTA> listSidang = repoSidang.getFilteredSidang(nip, keyword, keyword);

        Logger logger = LoggerFactory.getLogger(KoordinatorController.class);
        logger.info("Sidang: {}", listSidang);
        logger.info("keyword: {}", keyword);

        model.addAttribute("showContainer", true);
        model.addAttribute("listSidang", listSidang);
        return "dosen/dashboardKoordinator";
    }

}

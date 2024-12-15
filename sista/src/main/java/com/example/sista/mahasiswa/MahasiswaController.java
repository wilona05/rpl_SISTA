package com.example.sista.mahasiswa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sista.Sidang.Sidang;
import com.example.sista.Sidang.SidangRepository;
import com.example.sista.dosen.DosenRepository;
import com.example.sista.Sidang.InfoSidang;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/sista/dashboardMahasiswa")
public class MahasiswaController {

    @Autowired
    SidangRepository repoSidang;

    @Autowired
    DosenRepository repoDosen;

    @Autowired
    MahasiswaRepository repoMahasiswa;
    
    @GetMapping()
    public String dashboard(Model model, HttpSession httpSession){
        String email = (String) httpSession.getAttribute("email");
        String npm = repoMahasiswa.getNpmMahasiswa(email);
        List<Sidang> listSidang = repoSidang.getSidangByMahasiswa(npm);
        model.addAttribute("showContainer", false);
        model.addAttribute("listSidang", listSidang);
        return "mahasiswa/dashboardMahasiswa";
    }

    //halaman infosidang mahasiswa
    @GetMapping("infoSidangMahasiswa")
    public String infoSidang(){
        return "mahasiswa/infoSidangMahasiswa";
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
        return "mahasiswa/infoSidangMahasiswa"; // Return the Thymeleaf template name
    }

}

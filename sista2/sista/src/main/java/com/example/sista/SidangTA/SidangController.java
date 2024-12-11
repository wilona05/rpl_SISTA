package com.example.sista.SidangTA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sista.Users.User;
import com.example.sista.Users.UserService;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class SidangController {

    @Autowired
    private SidangRepository repoSidang;

    @Autowired
    private UserService userService;

    @GetMapping("/daftarSidang")
    public String daftarSidang(Model model) {
        List<User> dosenList = userService.getAllDosen();
        model.addAttribute("dosenList", dosenList);
        return "dosen/registrasiSidang";
    }

    @PostMapping("/daftarSidang")
    public String registerSidang(@RequestParam("nama") String nama, @RequestParam("npm") String npm,
            @RequestParam("judulTA") String judulTA, @RequestParam("jenisTA") int jenisTA,
            @RequestParam("dosenPembimbing") String dosenPembimbing, @RequestParam("jadwal") String jadwalString,
            @RequestParam("tempat") String tempat, @RequestParam("dosenPenguji1") String dosenPenguji1,
            @RequestParam("dosenPenguji2") String dosenPenguji2, Model model) {
        String email = npm + "@student.edu";
        Timestamp jadwal = Timestamp.valueOf(jadwalString.replace("T", " "));
        SidangTA sidangTA = new SidangTA(nama, npm, email, judulTA, jenisTA, jadwal, tempat, dosenPembimbing,
                dosenPenguji1, dosenPenguji2);
        boolean success = this.repoSidang.registerSidang(sidangTA);
        if (success) {
            model.addAttribute("sidangTA", sidangTA);
            // tambahkan edit sidang
            return "dosen/infoSidang";
        } else {
            model.addAttribute("error", "Gagal menyimpan sidang");
            return "dosen/registrasiSidang";
        }
    }
}

package com.example.sista.Admin;

import com.example.sista.Users.UserRepository;
import com.example.sista.Users.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/sista/dashboardAdmin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping()
    public String login() {
        return "admin/DashboardAdmin";
    }

    @GetMapping("/daftarPengguna")
    public String daftarPengguna(Model model) {
        List<User> user = this.userRepo.findAll();
        model.addAttribute("user", user);
        return "admin/DaftarPengguna";
    }

    @GetMapping("/akun")
    public String akun(@RequestParam(name="userID", required = false, defaultValue = "") String noInduk, Model model){
        
        if(noInduk == null || noInduk.isEmpty()){ //halaman untuk registrasi akun
            model.addAttribute("header", "Registrasi Akun");
            model.addAttribute("registrasiAkun", true);
        }else{ //halaman untuk lihat info akun
            User user = this.userRepo.getUserByID(noInduk);
            model.addAttribute("user", user);
            model.addAttribute("header", "Informasi Akun");
            model.addAttribute("informasiAkun", true);
        }
        
        return "admin/Akun";
    }

    @PostMapping("/akun")
    public String register(@RequestParam("noInduk") String noInduk, @RequestParam("nama") String nama,
            @RequestParam("email") String email, @RequestParam("passwords") String passwords,
            @RequestParam("role") String role, Model model) {
        User user = new User(noInduk, nama, email, passwords, role);
        model.addAttribute("user", user);
        boolean success = this.userRepo.register(user);
        if (success) {
            model.addAttribute("user", user);
            model.addAttribute("header", "Informasi Akun");
            model.addAttribute("informasiAkun", true);
            model.addAttribute("success", "Data berhasil disimpan");
            return "admin/Akun";
        } else {
            model.addAttribute("header", "Registrasi Akun");
            model.addAttribute("registrasiAkun", true);
            model.addAttribute("error", "Gagal menyimpan data");
            return "admin/Akun";
        }
    }
}
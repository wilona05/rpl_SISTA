package com.example.sista.Users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/sista")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    // halaman info sidang untuk dosen
    @GetMapping("/infoSidang")
    public String redirectToInfoSidang() {
        return "dosen/InfoSidang";
    }

    // halaman dashboard dosen
    @GetMapping("/dashboardDosen")
    public String redirectToDashboardDosen() {
        return "dosen/DashboardDosen";
    }

    // terima submission login
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String passwords, Model model) {
        String user = this.repo.login(email, passwords);
        if (user == null) { // user tidak ditemukan
            model.addAttribute("email", email);
            model.addAttribute("passwords", passwords);
            model.addAttribute("error", "Email atau password salah");
            return "Login";
        } else { // user ditemukan
            if (user.contains("@student.edu")) {
                return "mahasiswa/DashboardMahasiswa";
            } else if (user.contains("@dosen.edu")) {
                return "dosen/DashboardDosen";
            } else {
                return "admin/DashboardAdmin";
            }
        }
    }
}
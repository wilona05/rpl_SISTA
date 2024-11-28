package com.example.sista.Admin;

import com.example.sista.Users.UserRepository;
import com.example.sista.Users.User;

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
@RequestMapping("/sista/dashboardAdmin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping()
    public String login(){
        return "dashboard-admin";
    }

    @GetMapping("/daftarPengguna")
    public String daftarPengguna(Model model){
        List<User> user = this.userRepo.findAll();
        model.addAttribute("user", user);
        return "daftarPengguna";
    }

    @GetMapping("/akun")
    public String akun(@RequestParam("userID") String noInduk, Model model){
        User user = this.userRepo.getUserByID(noInduk);
        model.addAttribute("user", user);

        if(user == null){ //halaman untuk registrasi akun
            model.addAttribute("header", "Registrasi Akun");
            model.addAttribute("registrasiAkun", true);
        }else{ //halaman untuk lihat info akun
            model.addAttribute("header", "Informasi Akun");
            model.addAttribute("informasiAkun", true);
        }
        
        return "akun";
    }
}
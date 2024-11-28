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
    public String login(){
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String passwords, Model model){
        String user = this.repo.login(email, passwords);
        if(user == null){ //user tidak ditemukan
            model.addAttribute("email", email);
            model.addAttribute("passwords", passwords);
            model.addAttribute("error", "Email atau password salah");
            return "index";
        }else{ //user ditemukan
            if(user.contains("@student.edu")){
                return "dashboard-mahasiswa";
            }else if(user.contains("@dosen.edu")){
                return "dashboard-dosen";
            }else{
                return "redirect:/sista/dashboardAdmin";
            }
        }
    }
}

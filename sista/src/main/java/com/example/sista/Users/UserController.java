package com.example.sista.Users;

import com.example.sista.dosen.DosenRepository;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private DosenRepository dosenRepo;

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/sista/login";
    }

    //terima submission login
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String passwords, Model model, HttpSession httpSession){
        String user = this.repo.login(email, passwords);
        if (user == null) { // user tidak ditemukan
            model.addAttribute("email", email);
            model.addAttribute("passwords", passwords);
            model.addAttribute("error", "Email atau password salah");
            return "Login";
        }else{ //user ditemukan

            //simpan informasi user di session
            httpSession.setAttribute("email", user);

            if(user.contains("@student.edu")){
                return "redirect:/sista/dashboardMahasiswa";
            }else if(user.contains("@dosen.edu")){
                Boolean isKoordinator = dosenRepo.checkStatusKoord(user);

                if (isKoordinator) {
                    return "redirect:/sista/dashboardKoordinator";
                }
                return "redirect:/sista/dashboardDosen";
            }else{
                return "redirect:/sista/dashboardAdmin";
            }
        }
    }
}
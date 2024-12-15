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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public String daftarPengguna(Model model){
        List<User> user = this.userRepo.findAll();
        model.addAttribute("users", user);
        return "admin/DaftarPengguna";
    }

    @PostMapping("/daftarPengguna")
    public String daftarPengguna(Model model,
    @RequestParam(value = "noInduk", required = false) String noInduk,
    @RequestParam(value = "role", required = false) String role) {
        if(noInduk!=null && !noInduk.isEmpty()){ //ada filter noInduk
            User user = this.userRepo.getUserByID(noInduk);
            if(role!=null && !role.isEmpty()){ //ada filter role
                if(user==null || !user.getRole().equalsIgnoreCase(role)){
                    model.addAttribute("error", String.format("Pengguna dengan role '%s' dan nomor induk '%s' tidak ditemukan.", role, noInduk));
                    return "admin/DaftarPengguna";
                } 
            }
            
            if(user == null){ //user tidak ditemukan
                model.addAttribute("error", String.format("Pengguna dengan nomor induk '%s' tidak ditemukan.", noInduk));
            }else{ //user ditemukan
                model.addAttribute("searchedUser", user);
            }
        }else{ //tidak ada filter noInduk
            List<User> users = this.userRepo.findAll();
            if(role!=null && !role.isEmpty()){ //ada filter role
                List<User> filteredUsers = new ArrayList<>();
                for(User u : users){ //masukkan user dengan role yang sesuai filter
                    if(u.getRole().equalsIgnoreCase(role)){
                        filteredUsers.add(u);
                    }
                }
                users = filteredUsers;
            }
            model.addAttribute("users", users);
        }

        model.addAttribute("noInduk", noInduk);
        model.addAttribute("role", role);
        return "admin/DaftarPengguna";
    }

    @GetMapping("/registerAkun")
    public String register(){
        return "admin/RegisterAkun";
    }

    @PostMapping("/registerAkun")
    public String register(@RequestParam("noInduk") String noInduk, @RequestParam("nama") String nama,
    @RequestParam("role") String role, Model model) {
        String email = noInduk;
            if(role.equalsIgnoreCase("Mahasiswa")) email += "@student.edu";
            else email += "@dosen.edu";

            String passwords = randomPassword();

            User user = new User(noInduk, nama, email, passwords, role);
            model.addAttribute("user", user);
            boolean success = this.userRepo.register(user);
            if(success){
                model.addAttribute("user", user);
                model.addAttribute("editRole", false);
                return "admin/InfoAkun";
            }else{
                model.addAttribute("error", "Gagal menyimpan data");
                return "admin/RegisterAkun";
            } 
        }

    @GetMapping("/informasiAkun")
    public String akun(@RequestParam(name="userID") String noInduk, Model model){
            User user = this.userRepo.getUserByID(noInduk);
            model.addAttribute("user", user);
            model.addAttribute("editRole", false);
        return "admin/InfoAkun";
    }

    @GetMapping("/editAkun")
    public String editAkun(@RequestParam(name="userID") String noInduk, Model model){
        User user = this.userRepo.getUserByID(noInduk);
        model.addAttribute("user", user);
        model.addAttribute("editRole", true);
        return "admin/InfoAkun";
    }

    @PostMapping("/editAkun")
    public String editAkun(@RequestParam(name="userID") String noInduk, @RequestParam("role") String role,  Model model){
        boolean success = this.userRepo.editRoleDosen(noInduk, role);
        if(success){
            User user = this.userRepo.getUserByID(noInduk);
            model.addAttribute("user", user);
            return "admin/InfoAkun";
        }else{
            model.addAttribute("error", "Edit role gagal");
            return "admin/editAkun";
        }
    }

    private String randomPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String password = "";
        for(int i=0; i<8; i++){
            int idx = random.nextInt(characters.length());
            password += characters.charAt(idx);
        }
        return password;
    }
}
package com.example.sista.mahasiswa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sista/dashboardMahasiswa")
public class MahasiswaController {

    @GetMapping()
    public String dashboard(){
        return "mahasiswa/dashboardMahasiswa";
    }

    //halaman infosidang mahasiswa
    @GetMapping("infoSidangMahasiswa")
    public String infoSidang(){
        return "mahasiswa/infoSidangMahasiswa";
    }
}

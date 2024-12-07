package com.example.sista.dosen;

import com.example.sista.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class DosenController {

    @GetMapping()
    public String dashboard(){
        return "dosen/dashboardDosen";
    }

    @GetMapping("/infoSidang")
    public String infoSidang(){
        return "dosen/infoSidang";
    }

    @PostMapping("/lihatSidang")
    public String lihatSidang(@RequestParam(name="pickRole", required = true, defaultValue = "")String role, Model model){
        // TO-DO
        return "dashboardDosen";
    }
}

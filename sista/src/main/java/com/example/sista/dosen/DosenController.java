package com.example.sista.dosen;

import com.example.sista.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sista/dashboardDosen")
public class DosenController {

    @GetMapping()
    public String dashboard(){
        return "dosen/dashboardDosen";
    }
}

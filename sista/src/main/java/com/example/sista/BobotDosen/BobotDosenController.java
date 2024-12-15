package com.example.sista.BobotDosen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sista/dashboardKoordinator")
public class BobotDosenController {

    @Autowired
    private BobotDosenRepository repo;

    @GetMapping("/bobotDosen")
    public String bobotDosen(Model model) {
        List<BobotDosen> bobotDosen = this.repo.findAll();
        model.addAttribute("bobotDosen", bobotDosen);
        model.addAttribute("edit", false);
        return "dosen/bobotDosen";
    }

    @GetMapping("/editBobotDosen")
    public String editBobotDosen(Model model){
        List<BobotDosen> bobotDosen = this.repo.findAll();
        model.addAttribute("bobotDosen", bobotDosen);
        model.addAttribute("edit", true);
        return "dosen/bobotDosen";
    }

    @PostMapping("/saveBobotDosen")
    public String saveBobotDosen(@RequestParam Map<String, String> bobotData){ //bobotData = <bobot{idBobot}, bobot>
        for(String key : bobotData.keySet()){
            String idKey = key.replace("bobot", ""); //bobot1 --> 1
            Integer id = Integer.valueOf(idKey);
            BigDecimal newBobot= new BigDecimal(bobotData.get(key));
            this.repo.updateBobot(id, newBobot);
        }
        return "redirect:/sista/dashboardKoordinator/bobotDosen";
    }
}

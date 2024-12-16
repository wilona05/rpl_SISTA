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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public String saveBobotDosen(@RequestParam Map<String, String> bobotData,
        @RequestParam Map<String, String> tglBerlakuData){ //bobotData = <bobot{idBobot}, bobot>
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for(String key : bobotData.keySet()){
                if(key.startsWith("bobot")){
                    String idKey = key.replace("bobot", ""); //bobot1 --> 1
                    Integer id = Integer.valueOf(idKey);
                    BigDecimal newBobot= new BigDecimal(bobotData.get(key));
    
                    String tglBerlakuKey = "tglBerlaku" + id;
                    java.sql.Date tglBerlaku = null;
                    try {
                        tglBerlaku = new java.sql.Date(dateFormat.parse(tglBerlakuData.get(tglBerlakuKey)).getTime());
                    } catch (ParseException e) {
                        throw new RuntimeException("Tanggal tidak valid");
                    }
    
                    this.repo.updateBobot(id, newBobot, tglBerlaku);
                }
            }
            return "redirect:/sista/dashboardKoordinator/bobotDosen";
    }
}

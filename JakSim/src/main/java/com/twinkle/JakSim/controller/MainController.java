package com.twinkle.JakSim.controller;

import com.twinkle.JakSim.model.service.account.FileService;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final TrainerService trainerService;
    private final FileService fileService;
    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal User info) {
        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
            model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));
        }
        //로그인했을떄만 작동함.
        model.addAttribute("head_title", "작심득근");
        model.addAttribute("trainer", trainerService.searchTrainerForMainPage());

        return "content/index";
    }
}
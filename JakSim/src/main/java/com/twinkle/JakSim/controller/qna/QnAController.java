package com.twinkle.JakSim.controller.qna;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnAController {
    private final String defaultPath = "content/qna/";
    @GetMapping("/qna/list")
    public String listPage(){
        return String.format(defaultPath + "list");
    }

    @GetMapping("/qna/detail")
    public String detailPage(@AuthenticationPrincipal User user, Model model){
        return String.format(defaultPath + "detail");
    }
}

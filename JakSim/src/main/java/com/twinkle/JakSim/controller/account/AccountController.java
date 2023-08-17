package com.twinkle.JakSim.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    private final String defaultPath = "content/account/";

    @GetMapping("/account/{num}")
    public String accountPages(@PathVariable("num") int num, Model model) {
        model.addAttribute("head_title", (num == 4) ? "회원가입 성공" : "회원가입");
        return (num == 4) ? String.format(defaultPath + "account_fin") : String.format(defaultPath + "account_" + num);
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        model.addAttribute("head_title", "로그인");

        String url = request.getHeader("Referer");
        if (url != null && !url.contains("/login")) {
            request.getSession().setAttribute("prevPage", url);
        }

        return String.format(this.defaultPath + "login");
    }

    @GetMapping("/account/delprocess")
    public String deleteProcess() {
        return "redirect:/logout";
    }
}
